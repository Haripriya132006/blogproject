import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "../css/Profile.css";

function MyPosts({ userid: propUserId }) {
  const location = useLocation();
  const navigate = useNavigate();
  const [posts, setPosts] = useState([]);

  // Use prop first, fallback to location.state
  const userid = propUserId || location.state?.userid;

  useEffect(() => {
    if (!userid) return;

    fetch(`http://localhost:8080/post/user/${userid}`)
      .then(res => res.json())
      .then(data => setPosts(data))
      .catch(err => console.log(err));
  }, [userid]);

  // Delete post
  const deletePost = async (postid) => {
    if (!window.confirm("Are you sure you want to delete this post?")) return;

    try {
      const res = await fetch(`http://localhost:8080/post/delete/${postid}`, {
        method: "DELETE",
      });
      if (!res.ok) throw new Error("Failed to delete post");

      setPosts(prev => prev.filter(p => p.postid !== postid));
      alert("Post deleted successfully");
    } catch (err) {
      console.error(err);
      alert("Error deleting post");
    }
  };

  // Navigate to edit page using postid param
  const editPost = (post) => {
    navigate(`/editpost/${post.postid}`, { state: { userid } });
  };

  return (
    <div>
      <h1 style={{ padding: "20px 25px", margin: 0 }}>My Posts</h1>

      {posts.length === 0 && (
        <p style={{ padding: "0 25px" }}>No posts yet</p>
      )}

      <div className="posts-container">
        {posts.map(post => (
          <div key={post.postid} className="post-card">
            <h2>{post.title}</h2>

            {post.imageurl && (
              <img src={post.imageurl} alt="post" />
            )}

            <p>{post.content}</p>

            <button onClick={() => editPost(post)}>
              Edit
            </button>

            <button 
              onClick={() => deletePost(post.postid)}
              style={{ background: "#ef4444", marginTop: "5px" }}
            >
              Delete
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyPosts;