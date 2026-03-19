import { useState } from "react";

const api="http://localhost:8080/user/adduser"
function Signincomp(){
    const [username,setUsername]=useState("");
    const [email,setEmail]=useState("");
    const [password,setPassword]=useState("");

    const Adduser=async ()=>{
        const userData={username:username,
            email:email,
            password:password
        };
        try{
            const response=await fetch(api,{
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body:JSON.stringify(userData)
            })
            if(!response.ok){
                throw new Error("Failed to add user");
            }
            const data =await response.text();
            alert(data);
        }
        catch(error){
            alert("Error signing in");
        }
        
    }
    return (
    <>
    
        
        <label>username</label>
        <input type="text"  onChange={(e)=>{setUsername(e.target.value)}}/><br/>
        <label>email</label>
        <input type="text" onChange={(e)=>{setEmail(e.target.value)}}/> <br/>      
        <label>password</label>
        <input type="text" onChange={(e)=>{setPassword(e.target.value)}}/>
        <button onClick={Adduser}>Signin</button>
        
    </>
)
}
export default Signincomp;