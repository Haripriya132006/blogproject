import { useState } from "react";
import "../css/Signin.css";
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
        <div className="signin-container">
            <div className="signin-box">

                <h2>Signup</h2>

                <label>Username</label>
                <input
                    type="text"
                    onChange={(e)=>setUsername(e.target.value)}
                />

                <label>Email</label>
                <input
                    type="text"
                    onChange={(e)=>setEmail(e.target.value)}
                />

                <label>Password</label>
                <input
                    type="password"
                    onChange={(e)=>setPassword(e.target.value)}
                />

                <button onClick={Adduser}>
                    Signup
                </button>

            </div>
        </div>
    );
}
export default Signincomp;