import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function MainPage({userid}){

    const [posts,setPosts] = useState([]);
    const navigate = useNavigate();

    useEffect(()=>{

        if(!userid) return;   // 🚨 VERY IMPORTANT

        fetch(`http://localhost:8080/post/feed/${userid}`)
        .then(res => res.json())
        .then(data => setPosts(data))
        .catch(err => console.log(err));

    },[userid]);

    const getPreview = (text) => {
        const words = text.split(" ");
        return words.slice(0,20).join(" ") + "...";
    };

    const openPost = (post) => {
        navigate("/postdetails",{state:{post:post ,userid:userid}});
    };

    const logout = () => {

        localStorage.clear();
        navigate("/");

    };

    const goProfile = () => {

        navigate("/profile",{state:{userid:userid}});

    };

    return(
        <>

        <div
        style={{
            display:"flex",
            justifyContent:"space-between",
            alignItems:"center",
            padding:"10px",
            borderBottom:"1px solid gray"
        }}
        >

            <h1>All Posts</h1>

            <div>

                <button onClick={goProfile}>
                    Go To Profile
                </button>

                <button onClick={logout}>
                    Logout
                </button>

            </div>

        </div>


        <div
        style={{
            display:"grid",
            gridTemplateColumns:"repeat(3,1fr)",
            gap:"20px",
            padding:"20px"
        }}
        >

            {posts.map((post) => (

                <div 
                    key={post.postid} 
                    style={{
                        border:"1px solid black",
                        padding:"10px",
                        borderRadius:"8px"
                    }}
                >

                    <h2>{post.title}</h2>

                    <p>{getPreview(post.content)}</p>

                    <img 
                        src={post.imageurl}
                        width="100%"
                        alt="post"
                    /><br/><br/>

                    <button onClick={()=>openPost(post)}>
                        Add Comment
                    </button>

                </div>

            ))}

        </div>

        </>
    )
}

export default MainPage;