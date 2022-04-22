import { removeCookies } from 'cookies-next';
import Router from 'next/router'




export function Logout() { 
    const logout = () =>{
        removeCookies("ssToken", 123);
        Router.push('/')
    }
    
    
    return ( 
        
        <div>
            
        <button
          type="submit"
          onClick={logout}
          style={{background:'red', 
                cursor:'pointer',
                padding:'10px',
                margin:'10px',
                float: 'right'}}
            >Logout</button>

        </div>

    )
}
