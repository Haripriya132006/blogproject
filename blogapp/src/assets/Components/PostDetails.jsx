import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import "../css/Postdetails.css";

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

    return (
    <div className="details-container">

        {/* LEFT: POST */}
        <div className="post-section">

        <h1>{post.title}</h1>

        {post.imageurl && (
            <img src={post.imageurl} alt="post" />
        )}

        <p>{post.content}</p>

        </div>

        {/* RIGHT: COMMENTS */}
        <div className="comments-section">

        <h2>Comments</h2>

        <div className="comments-list">
            {comments.map((c) => (
            <div key={c.commentid} className="comment-card">
                <b>{c.user.username}</b>
                <p>{c.comment}</p>
            </div>
            ))}
        </div>

        <div className="comment-input">
            <input
            type="text"
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            placeholder="Write a comment..."
            />

            <button onClick={addComment}>
            Add
            </button>
        </div>

        </div>

    </div>
    );
}

export default PostDetails;