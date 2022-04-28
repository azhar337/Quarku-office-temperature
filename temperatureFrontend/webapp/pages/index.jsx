import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'
import Link from 'next/link'
import { Button, ButtonGroup, Center, Heading, Flex } from '@chakra-ui/react'
import theme from '../styles/theme'

export default function Home() {
  return (
    // <div className={styles.container}>
    //   <Head>
    //     <title>Office Temperature</title>
    //     <meta name="description" content="Its fun and game until you forget your sweater and get fever" />
    //     <link rel="icon" href="/fav.PNG" />
    //   </Head>
      
    //   <main className={styles.main}>
    //     <h1 className={styles.title}>
    //       What my office <b>temperature?</b>
    //     </h1>

    //     <p className={styles.description}>
    //       Get your office temperature analysis
    //     </p>
      
    //     <div className={styles.grid}>
    //       <Link href="/register">
    //         <Button colorScheme='blue' size='lg' m='10px'>Register</Button>
    //       </Link>

    //       <Link href="/login">
    //           <Button colorScheme='teal' size='lg' m='10px'>Login</Button>
    //       </Link>
    //     </div>
    //   </main>
     
    //   <footer className={styles.footer}>
    //     <a
    //       href="https://www.google.com/search?q=air+conditioner&rlz=1C1CHBF_enMY996MY996&oq=Aircondi&aqs=chrome.1.69i57j0i10i512l5j0i10i457i512j0i402j0i10i512l2.9213j0j7&sourceid=chrome&ie=UTF-8"
    //       target="_blank"
    //       rel="noopener noreferrer"
    //     >
    //       Powered by air cond
 
    //     </a>
    //   </footer>
    // </div>

    <Center>
      <Flex
        flexDir="column"  
        marginTop="10em"
      >
      <Heading as='h1' m='30px'>My office <b>temperature</b></Heading>
       
        <Flex 
          flexDir="row"
          w='100%'
          m='30px'
        >
          <Center>
              <Link href="/register">
              <Button colorScheme='blue' size='lg' m='10px'>Register</Button>
            </Link>

            <Link href="/login">
                <Button colorScheme='teal' size='lg' m='10px'>Login</Button>
            </Link>
           </Center>
  
           </Flex>
          
      </Flex>

    </Center>
  )
}
