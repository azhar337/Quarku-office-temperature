import Router from 'next/router'
import jwt from 'jsonwebtoken'; 
import { checkCookies, getCookies, removeCookies } from 'cookies-next';


export const auth = () => {

    const jwtKey = process.env.NEXT_PUBLIC_JWTPub; 
    const privKey = process.env.NEXT_PUBLIC_COOKIESPRIV;

      if(checkCookies('ssToken', 123)){
          const cookie = getCookies('ssToken',123);
          try{
           const tokenCookie = jwt.verify(cookie.ssToken , privKey)

             if(tokenCookie && jwt.verify(tokenCookie.token, jwtKey)){

                 return tokenCookie.token   
              }else{
                // console.log("3")
                 return false
             }
        
         }catch{
            // console.log("2")
             removeCookies("ssToken", 123);
         }
        }else{
            // console.log("1")
            return false
         }
}

