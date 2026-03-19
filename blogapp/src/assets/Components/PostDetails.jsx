import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";

function PostDetails(){

    const location = useLocation();
    const post = location.state?.post;
    const userid = location.state?.userid;

    const [comments,setComments] = useState([]);
    const [newComment,setNewComment] = useState("");

    // prevent crash if page refreshed
    if(!post){
        return <h2>Post not found</h2>;
    }

    const loadComments = () => {
        fetch("http://localhost:8080/comment/post/"+post.postid)
        .then(res => res.json())
        .then(data => setComments(data))
        .catch(err => console.log(err));
    };

    useEffect(()=>{
        loadComments();
    },[]);

    const addComment = async () => {

        if(newComment.trim() === ""){
            alert("Write a comment first");
            return;
        }

        const params = new URLSearchParams();

        params.append("userid", userid);
        params.append("postid", post.postid);
        params.append("comment", newComment);

        try{

            const response = await fetch("http://localhost:8080/comment/add",{
                method:"POST",
                headers:{
                    "Content-Type":"application/x-www-form-urlencoded"
                },
                body: params
            });

            const data = await response.text();

            // alert(data);

            setNewComment("");

            loadComments();

        }catch(err){
            console.log(err);
        }
    };

    return(

        <div style={{display:"flex"}}>

            {/* POST SECTION */}

            <div style={{flex:2,padding:"20px"}}>

                <h1>{post.title}</h1>

                <img
                src={post.imageurl}
                width="400"
                alt="post"
                />

                <p>{post.content}</p>

            </div>


            {/* COMMENTS SECTION */}

            <div
            style={{
                flex:1,
                borderLeft:"1px solid gray",
                padding:"20px"
            }}
            >

                <h2>Comments</h2>

                {comments.map((c)=>(
                    <div key={c.commentid} style={{marginBottom:"10px"}}>

                        <b>{c.user.username}</b>

                        <p>{c.comment}</p>

                    </div>
                ))}

                <input
                type="text"
                value={newComment}
                onChange={(e)=>setNewComment(e.target.value)}
                placeholder="Write comment"
                />

                <button onClick={addComment}>
                    Add
                </button>

            </div>

        </div>

    );
}

export default PostDetails;