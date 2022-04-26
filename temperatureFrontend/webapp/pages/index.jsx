import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'
import Link from 'next/link'
import { ColorModeScript } from '@chakra-ui/react'
import theme from '../styles/theme'

export default function Home() {
  return (
    <div className={styles.container}>
      <Head>
        <title>Office Temperature</title>
        <meta name="description" content="Its fun and game until you forget your sweater and get fever" />
        <link rel="icon" href="/fav.PNG" />
      </Head>
      <ColorModeScript />
      <main className={styles.main}>
        <h1 className={styles.title}>
          What my office <b>temperature?</b>
        </h1>

        <p className={styles.description}>
          Get your office temperature analysis
        </p>
      
        <div className={styles.grid}>
          <Link href="/register">
            <a className={styles.card}>
              <h2>Register &rarr;</h2>
        
            </a>
          </Link>

          <Link href="/login">
            <a className={styles.card}>
              <h2>Login &rarr;</h2>
            </a>
          </Link>
        </div>
      </main>
     
      <footer className={styles.footer}>
        <a
          href="https://www.google.com/search?q=air+conditioner&rlz=1C1CHBF_enMY996MY996&oq=Aircondi&aqs=chrome.1.69i57j0i10i512l5j0i10i457i512j0i402j0i10i512l2.9213j0j7&sourceid=chrome&ie=UTF-8"
          target="_blank"
          rel="noopener noreferrer"
        >
          Powered by air cond
 
        </a>
      </footer>
    </div>
  )
}
