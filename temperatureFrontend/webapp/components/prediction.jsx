import axios from '../base-axios';

export function Prediction({token, id}) { 
    
    const makePrediction = async (event) =>{
        event.preventDefault();
        let predictionTime = new Date(event.target.date.value).getTime();
        let trimmedTime = predictionTime.toString().slice(0,10);
        await axios.post(`api/prediction/${id}/${trimmedTime}`,
        {},
        { headers: {
            Authorization: `Bearer ${token}`
        },})
        .then(res => alert("Predicted result: "+res.data))
        .catch(err => console.log(err)); 

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
