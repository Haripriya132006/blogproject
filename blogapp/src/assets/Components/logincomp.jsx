import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Logincomp(){

    const api = "http://localhost:8080/user/check";

    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");

    const navigate = useNavigate();

    const Checkuser = async () => {

        const userdata = {
            email: email,
            password: password
        };

        try{

            const response = await fetch(api,{
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body:JSON.stringify(userdata)
            });

            if(!response.ok){
                alert("Login request failed");
                return;
            }

            const data = await response.json();

            console.log("LOGIN RESPONSE:", data); // ✅ DEBUG

            // ✅ FIX: check using userid from backend
            if(data.user_id === -1){

                alert("User doesn't exist. Please signup.");
                navigate("/signin");

            }
            else{

                alert("Login successful");

                // ✅ Store in localStorage (IMPORTANT)
                localStorage.setItem("userid", data.user_id);
                localStorage.setItem("username", data.username);

                // ✅ Navigate with correct fields
                navigate("/first",{
                    state:{
                        username: data.username,
                        userid: data.user_id
                    }
                });

            }

        }
        catch(error){

            console.error("ERROR:", error); // ✅ DEBUG
            alert("Server error. Please try again.");
        }
    }

    return(
        <div>

            <h2>Login</h2>

            <label>Email</label>
            <input
                type="text"
                value={email}
                onChange={(e)=>setEmail(e.target.value)}
            />
            <br/>

            <label>Password</label>
            <input
                type="password"
                value={password}
                onChange={(e)=>setPassword(e.target.value)}
            />
            <br/>

            <button onClick={Checkuser}>
                Login
            </button>

            <br/><br/>

            <p>Don't have an account?</p>

            <button onClick={()=>navigate("/signin")}>
                Signup
            </button>

        </div>
    )
}

export default Logincomp;