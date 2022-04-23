import styles from '../../styles/Home.module.css'
import axios from '../../base-axios'
import { useState, useEffect } from "react"
import Router from 'next/router'
import jwt from 'jsonwebtoken'; 
import { setCookies, checkCookies, getCookies, removeCookies } from 'cookies-next';
import {Logout} from '../../components/logout'
import {Upload} from '../../components/upload'
import {auth} from '../../utils/cookies'
import Link from 'next/link'



export default function Dashboard(userData) { 
    const [files, createFiles] = useState("string");
    let token;
    if (auth()){
             token = auth()
          
         }else{
            // Router.push('/')
         }

       axios.post('api/files/',{},
            {headers: {Authorization: `Bearer ${token}`}})
            .then(res => createFiles(res.data))
            .catch(err => console.log(err));

         let arr = files.split(',').slice(1)

//make upload trigger a function that change a state that display here 
    return ( 
        <div>
            
            <Logout />
            <Upload token={token}/>

            <ul>
                {arr.map((arr, index)=> <Link href={`/dashboard/${index + 1}`}><a><li>File {index + 1}</li></a></Link> )}
            </ul>
        </div>

    )
}


///TODO check the cookies and authenticate