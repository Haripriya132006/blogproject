import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../css/Postcomp.css";
function Postcomp(){

    const [title,setTitle] = useState("");
    const [content,setContent] = useState("");
    const [image,setImage] = useState(null);
    const [visibility,setVisibility] = useState("PUBLIC");

    const location = useLocation();
    const navigate = useNavigate();

    const userid = location.state?.userid;

    const Post = async () => {

        const formData = new FormData();

        formData.append("userid",userid);
        formData.append("title",title);
        formData.append("content",content);
        formData.append("image",image);
        formData.append("visibility",visibility);

        try{

            const response = await fetch(
                "http://localhost:8080/post/create",
                {
                    method:"POST",
                    body:formData
                }
            );

            const data = await response.text();

            alert(data);

            navigate("/first",{state:{userid:userid}});

        }
        catch(error){
            alert("Error posting");
        }

    }

    return (
        <div className="post-container">
            <div className="post-box">

                <h2>Create Post</h2>

                <label>Title</label>
                <input type="text" onChange={(e)=>setTitle(e.target.value)}/>

                <label>Content</label>
                <textarea onChange={(e)=>setContent(e.target.value)}></textarea>

                <label>Image</label>
                <input type="file" onChange={(e)=>setImage(e.target.files[0])}/>

                <label>Who can see this post?</label>
                <select onChange={(e)=>setVisibility(e.target.value)}>
                    <option value="PUBLIC">Everyone</option>
                    <option value="FOLLOWERS">My Followers</option>
                    <option value="FOLLOWING">People I Follow</option>
                </select>

                <button onClick={Post}>Post</button>

            </div>
        </div>
    );
}

export default Postcomp;