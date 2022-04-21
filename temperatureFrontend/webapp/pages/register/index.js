import styles from '../../styles/Home.module.css'
import { sha256 } from 'js-sha256';
import axios from '../../base-axios'
import { useState, useEffect } from "react"
import Router from 'next/router'



export default function Home() {

    const [status, createStatus] = useState();

    const register = async (event) => {
        event.preventDefault()
        let _email = event.target.email.value;
        let hashPassword = await sha256(event.target.password.value)
        let confHashPassword = await sha256(event.target.confirmPassword.value)

       if(_email == "" || hashPassword == "" ||confHashPassword == "" ){        
           alert("empty field");
           return;
       }

        if (hashPassword == confHashPassword){
            
            const data = {
                email:_email,
                status:false,
                password: hashPassword,
            }
           await axios.post('auth/reg', data)
                .then(request => createStatus(request.data))
                .catch(err => console.log(err));
            

        }else{
            alert("not same password");
            event.target.password.value = "";
            event.target.confirmPassword.value = "";
            return;
        }
    }

    useEffect( () => {
        if(status){
            switch(status) {
                case "token send":
                    alert("check verification in your email");
                    Router.push("/activate/")
                    break;
                
                case "Already registered":
                    alert("Already register, login now");
                    Router.push("/login/")
                    break;

                default:
                    alert("Error Occurred");
                    Router.reload(window.location.pathname)
                    break;
            }
        }
    },[status]);


    return (
        
        <div className={styles.container}>
            <h1 className={styles.title}> Register </h1>
            <div className={styles.description}>            
                    <form  method="POST" onSubmit={register}>
                        <label for="email">Email:</label>
                        <input type="email" id="email"  name="email"/>
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" ></input>
                        <label for="confirmPassword"> Confirm Password:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" ></input><br></br>
                        <input type="submit" value="Register"></input>
                    </form>
            </div>
        </div>
    )
}
