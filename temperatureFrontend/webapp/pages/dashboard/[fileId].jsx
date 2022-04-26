import {useRouter} from 'next/router'
import {auth} from '../../utils/cookies'
import axios from '../../base-axios'
import { useState, useEffect } from "react"
import {Logout} from '../../components/logout'
import {Prediction} from '../../components/prediction'
import { 
    Chart as ChartJS,
    CategoryScale, 
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler,
  } from 'chart.js';
  
  ChartJS.register(
    CategoryScale,
    LineElement,
    LinearScale,
    PointElement,
    Filler,
    Title,
    Tooltip,
    Legend
  );
  import {Line} from "react-chartjs-2";


export default function FileId(){

    const router = useRouter()

    const [rawData, createRawData ] = useState([]);
    const [Id, createId ] = useState([]);
    const [Row2, createRow2 ] = useState([]);
    const [Time, createTime ] = useState([]);
    const [Row4, createRow4 ] = useState([]);
    const [Row5, createRow5 ] = useState([]);
    const [Row6, createRow6 ] = useState([]);
    const [Temperature, createTemperature] = useState([]);
    const [Visual, createVisual] = useState();
    const [Table, createTable] = useState();

    const id = [];
    const row2 = [];
    const time = [];
    const row4 = [];
    const row5 = [];
    const row6 = [];
    const temperature = [];


    let token;
    if (auth()){
             token = auth()
          
         }else{
            // Router.push('/')
         }
         
    const getData = async (event) => {
      
        await axios.post(`api/data/${router.query.fileId}`,
        {},
        { headers: {
            Authorization: `Bearer ${token}`
        },})
        .then(res => createRawData(res.data.split('\n')))
        .catch(err => console.log(err));  

   
    }

      useEffect( () => { 
          if(rawData){
            
        rawData.forEach(function(raw){
            let _raw = raw.split(',')
            id.push(_raw[0])
            row2.push(_raw[1])
            time.push(_raw[2])
            row4.push(_raw[3])
            row5.push(_raw[4])
            row6.push(_raw[5])
            temperature.push(_raw[6])
         })
            createId(id)
            createRow2(row2)
            createTime(time)
            createRow4(row4)
            createRow5(row5)
            createRow6(row6)
            createTemperature(temperature)
       
        }
     },[rawData])
    
     useEffect( () => {

        const data = {
          labels: Time.slice(-20),
          datasets: [
              {
                  data:Temperature.slice(-20),
              },
          ],
      };
    
      const options = {
      plugins:{
          legend:{
              display:false,
          },
      },
      elements: {
          line:{
              tension: 0,
              borderWidth: 2,
              borderColor: "rgba(47,97,68,1)",
              fill: "start",
              background: "rgba(47,97,68,0.3)",
          },
          point:{
              radius: 0,
              hitRadius: 0,
          },
      },
      scales: {
          xAxis:{
              display: true,
          },
          yAxis:{
              display: true,
          },
      },
      };
      
      createVisual( <Line data={data} width={200} height={50} options={options} />);
    
    },[Temperature,Time]);

    useEffect(() =>{

        let table = <table>
            <tr>
            <th>Id</th>
            <th>Row2</th>
            <th>Time</th>
            <th>Row 4</th>
            <th>Row 5</th>
            <th>Row 6</th>
            <th>Temperature</th>
            </tr>
            {tableColumn()}
        </table>


        createTable(table)

    },[Temperature,Time,Id,Row2,Row4,Row5,Row6])

    const tableColumn = () =>{
        var rows = [];

        Temperature.slice(-20).forEach(function(temp,index){
            rows.push(
                <tr>
                    <td>{Id[index]}</td>
                    <td>{Row2[index]}</td>
                    <td>{Time[index]}</td>
                    <td>{Row4[index]}</td>
                    <td>{Row5[index]}</td>
                    <td>{Row6[index]}</td>
                    <td>{temp}</td>
                </tr>
            )
        })
        
        return rows
    }

    const filterTemperature = (event) => {

        event.preventDefault()
    
        const index = 0;
        let time = [];
        let temp = [];
        let r2 =[];
        let r4 = [];
        let id =[];
        let r5 =[];
        let r6 =[];
    
        if (event.target.FTmp.value > event.target.LTmp.value){
          alert("Invalid Request");
          return ;
        }
    
        Temperature.forEach(function(element) { 
          
            if (element >= event.target.FTmp.value && element <=event.target.LTmp.value){
              r2.push(Row2[index])
              r4.push(Row4[index])
              r5.push(Row5[index])
              r6.push(Row6[index])
              id.push(Id[index])  
              time.push(Time[index])
              temp.push(element)
            }
          });
          
          createId(id)
          createRow2(r2)
          createTime(time)
          createRow4(r4)
          createRow5(r5)
          createRow6(r6)
          createTemperature(temp)
      };
    
    
      // Monday, 1 November 2021 08:27:38.980 until  Monday, 14 March 2022 07:39:31.985
      const filterDate = (event) => {
       
    
        event.preventDefault()
        let startTime = new Date(event.target.FDate.value).getTime();
        let endTime = new Date(event.target.LDate.value).getTime();
    
        const index = 0;
        let time = [];
        let temp = [];
        let r2 =[];
        let r4 = [];
        let id =[];
        let r5 =[];
        let r6 =[];
    
        if (startTime > endTime){
          alert("Invalid Request");
          return ;
        }
    
        Time.forEach(function(element) { 
          
            if (element >= startTime && element <= endTime){
                r2.push(Row2[index])
                r4.push(Row4[index])
                r5.push(Row5[index])
                r6.push(Row6[index])
                id.push(Id[index])  
                time.push(element)
                temp.push(Temperature[index])
            }
          });
          
          createId(id)
          createRow2(r2)
          createTime(time)
          createRow4(r4)
          createRow5(r5)
          createRow6(r6)
          createTemperature(temp)
      };
    
      const reset = (event) => {
          console.log(id)
        createId(id)
        createRow2(row2)
        createTime(time)
        createRow4(row4)
        createRow5(row5)
        createRow6(row6)
        createTemperature(temperature)
      
      };


      const download = (event) => {

        var lineArray = [];
          Temperature.forEach(function (temperature, index) {
    
              let line = Id[index] + "," + Row2[index] + "," + Time[index] + "," + Row4[index] + "," + Row5[index] + "," + Row6[index] + "," + temperature;
              lineArray.push(line);
          });
          let csvContent = lineArray.join("\n");
         let csvData = new Blob([csvContent], {type: 'text/csv;charset=utf-8;'});
         let csvURL = window.URL.createObjectURL(csvData );
         window.open(csvURL);
      };



    return (

        <div>
              <Logout />
              <Prediction token={token} id={router.query.fileId} />
            {Visual}
            <button type="submit" onClick={getData}>Get Data</button>

        <form  onSubmit={filterTemperature}>
            <label for="FTmp">First temperature:</label>
            <input type="number" id="FTmp"  name="FTmp"/>
            <label for="LTmp">Last temperature:</label>
            <input type="number" id="LTmp" name="LTmp" ></input>
            <input type="submit" value="Submit"></input>
          </form>

          <form onSubmit={filterDate}>
            <label for="FDate">First Date:</label>
            <input type="datetime-local" id="FDate" name="FDate"/>
            <label for="LDate">Last Date:</label>
            <input type="datetime-local" id="LDate" name="LDate"/>
            <input type="submit" value="Submit"/>
          </form>

          <button
          type="submit"
          onClick={reset}
        >
          reset
        </button>
        
        <button
          type="submit"
          onClick={download}
        >
          download
        </button>

        {Table}
        </div>
    )
}