import styles from '../../styles/Home.module.css'
import { sha256 } from 'js-sha256';
import axios from '../../base-axios'
import { useState, useEffect } from "react"
import Router from 'next/router'
import jwt from 'jsonwebtoken'; 
import { setCookies, checkCookies, getCookies, removeCookies } from 'cookies-next';

export default function Login() {
    const jwtKey = process.env.NEXT_PUBLIC_JWTPub; 
    const privKey = process.env.NEXT_PUBLIC_COOKIESPRIV;

    if(checkCookies('ssToken', 123)){
        const cookie = getCookies('ssToken',123);
        try{
         const tokenCookie = jwt.verify(cookie.ssToken , privKey)
            if(tokenCookie){
                if(jwt.verify(tokenCookie.token, jwtKey)){
                Router.push("/dashboard/")   
                }
            }
        }catch{
            removeCookies("ssToken", 123);
        }
    }

    const [status, createStatus] = useState();
    
    const login = async (event) =>{
        event.preventDefault()
        let _email = event.target.email.value;
        let hashPassword = await sha256(event.target.password.value)

        const data = {
            email:_email,
            password: event.target.password.value, //TODO : remove this
        }

        await axios.post('auth/log', data)
                .then(req => createStatus(req.data))
                .catch(err => createStatus(err.response.statusText));
    }

    useEffect ( () =>{

        if(status){
            switch(status) {
                case "Unauthorized":
                    alert("Wrong Password");
                    break;
                case "Not Found":
                    alert("Email does not exist");
                    Router.push("/register/")
                case "Bad Request":
                    alert("Account not activated");
                    Router.push("/register/")
                
                default:
                    if (jwt.verify(status, jwtKey)){
                        const payload = {
                            token: status
                          }
                        const cookie = jwt.sign(payload,privKey,{expiresIn: 31556926})
                        setCookies('ssToken', cookie, 123);
                        Router.push("/dashboard/")
                    }
                    break;   
            }

        }

        },[status]);

    
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
