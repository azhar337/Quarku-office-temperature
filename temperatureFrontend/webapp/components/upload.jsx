import { removeCookies } from 'cookies-next';
import Router from 'next/router'
import { useState, useEffect } from "react";
import axios from '../base-axios'
import { v4 as uuidv4 } from 'uuid';


export function Upload({token}) { 
    const [file, setFile] = useState();
    const [createObjectURL, setCreateObjectURL] = useState();
    const [status, setStatus] = useState();
    
    const uploadToClient = (event) => {
        if (event.target.files && event.target.files[0]) {
          const i = event.target.files[0];
          setFile(i);
          setCreateObjectURL(URL.createObjectURL(i));
        }
    };

    const uploadToServer = async (event) => {

        const data = new FormData();
        const fileName = uuidv4();
        data.append("file", file);
        data.append("fileName", fileName );

        await axios.post('api/upload',
         data,
        { headers: {
           ContentType: 'multipart/form-data',
           Authorization: `Bearer ${token}`
        },})
        .then(res => location.reload())
        .catch(err => setStatus(err.response.statusText));
      };

      useEffect ( () =>{
        if(status){
          switch(status) {
            case "Bad Request":
                alert("Upload limit reached");
                break;
            default:
              alert("Upload fail")
              break;
          }

        }

      },[status])

      
    
    return ( 
        
        <div  
        style={{background:'Grey', 
        cursor:'pointer',
        padding:'10px',
        margin:'10px',
        float: 'right'}}>

        <input type="file" name="myFile" onChange={uploadToClient} />
        <button type="submit" onClick={uploadToServer}>Upload</button>
           
        </div>
    )
}

