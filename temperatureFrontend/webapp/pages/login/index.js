import styles from '../../styles/Home.module.css'
import { sha256 } from 'js-sha256';
import axios from '../../base-axios'
import { useState, useEffect } from "react"
import Router from 'next/router'

export default function Home() {
    const [status, createStatus] = useState();

    const login = async (event) =>{
        event.preventDefault()
        let _email = event.target.email.value;
        let hashPassword = await sha256(event.target.password.value)

        const data = {
            email:_email,
            password: event.target.password.value,
        }

        await axios.post('auth/login', data)
                .then(req => createStatus(req))
                .catch(err => createStatus(err.response.statusText));
    }


    
    return (

        <div className={styles.container}>
        <h1 className={styles.title}> Login </h1>
        <div className={styles.description}>            
                <form  method="POST" onSubmit={login}>
                    <label for="email">Email:</label>
                    <input type="email" id="email"  name="email"/>
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" ></input><br></br>
                    <input type="submit" value="Login"></input>
                </form>
        </div>
    </div>
    )
}
