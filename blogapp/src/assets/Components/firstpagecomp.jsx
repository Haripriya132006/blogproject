import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import MainPage from "./Mainpage";
import "../css/FirstPage.css";
function FirstPage(){

    const location = useLocation();
    const navigate = useNavigate();

    const [userid, setUserid] = useState(null);
    const [username, setUsername] = useState("");

    useEffect(() => {

        let id = location.state?.userid || localStorage.getItem("userid");
        let name = location.state?.username || localStorage.getItem("username");

        console.log("INIT USERID:", id);

        // 🚨 INVALID → redirect
        if(!id || id === "undefined"){
            navigate("/");
            return;
        }

        // ✅ store safely
        localStorage.setItem("userid", id);
        localStorage.setItem("username", name);

        setUserid(id);
        setUsername(name);

    }, []);

    // 🚨 prevent render until ready
    if(!userid){
        return <h2>Loading...</h2>;
    }

    const addpost = () => {
        navigate("/post",{state:{userid}});
    };

    return (
        <>
            <div className="firstpage-header">
                <h1>Welcome, {username} 👋</h1>

                <div className="firstpage-actions">
                    <button 
                        className="add-post-btn"
                        onClick={addpost}
                    >
                        + Add Post
                    </button>
                </div>
            </div>

            <MainPage userid={userid}/>
        </>
    );
}

export default FirstPage;