export default function Dashboard({totalCsv}) {
    console.log(totalCsv)
    return ( <h1>{totalCsv}</h1>)
}

export async function getStaticProps() {

    const posts = "test";
    return {
        props: {
         posts,
        },
      }

}