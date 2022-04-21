import styles from '../../styles/Home.module.css'
import axios from '../../base-axios'
import { useState, useEffect } from "react"
import Router from 'next/router'

export default function Home() {
    const [status, createStatus] = useState();


    const activate = async (event) => {
        event.preventDefault();
        let token = event.target.token.value
        await axios.post('auth/act/',{},
        {headers: {Authorization: `Bearer ${token}`}})
                .then(req => createStatus(req.data))
                .catch(err => createStatus(err.response.statusText));
    }

    useEffect( () =>{
        if(status){
            switch(status) {
                case true:
                    alert("Activation sucessful");
                    Router.push("/login/")
                    break;
                
                case false:
                    alert("Activation failed");
                    Router.reload(window.location.pathname)
                    break;

                case "Unauthorized":
                    alert("Time out register again");
                    Router.push("/register/")
                    break;

                default:
                    alert("Error occured");
                    Router.push("/register/")
                    break;
            }
        }
    },[status]);

    return (

        <div className={styles.container}>
            <h1 className={styles.title}> Activate </h1>
            <div className={styles.description}>            
                    <form  method="POST" onSubmit={activate}>
                        <label for="token">Put token here:</label>
                        <input type="text" id="token"  name="token"/>
                        <input type="submit" value="Register"></input>
                    </form>
            </div>
        </div>
    )
}
