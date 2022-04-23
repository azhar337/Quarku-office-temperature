export function Prediction() { 
    
    const makePrediction = async () =>{

    }
    
    return ( 
        
        <div  
        style={{background:'Grey', 
        cursor:'pointer',
        padding:'10px',
        margin:'10px',
        float: 'right'}}>
        <form  onSubmit={makePrediction}>
            <input type="datetime-local" id="date" name="date"/>
            <input type="submit" value="Predict"/>
        </form>  
        </div>

    )
}
