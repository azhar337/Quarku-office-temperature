import styles from '../../styles/Home.module.css'
import axios from '../../base-axios'
import { useState, useEffect } from "react"
import Router from 'next/router'
import jwt from 'jsonwebtoken'; 
import { setCookies, checkCookies, getCookies, removeCookies } from 'cookies-next';
import {Logout} from '../../components/logout'
import {Upload} from '../../components/upload'

export default function Dashboard() { 
    
    const [token, createToken] = useState();
    
    return ( 
        <div>
            <Logout />
            <Upload token={token}/>


        </div>

    )
}


///TODO check the cookies and authenticate