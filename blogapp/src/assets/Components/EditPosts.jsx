import { useState, useEffect } from "react";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import "../css/EditPost.css";   // ✅ IMPORTANT

function EditPost() {
  const { postid } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const userid = location.state?.userid;

  const [post, setPost] = useState(null);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [visibility, setVisibility] = useState("PUBLIC");
  const [image, setImage] = useState(null);

  useEffect(() => {
    if (!postid) return;

    fetch(`http://localhost:8080/post/${postid}`)
      .then(res => res.json())
      .then(data => {
        setPost(data);
        setTitle(data.title);
        setContent(data.content);
        setVisibility(data.visibility || "PUBLIC");
      })
      .catch(err => console.log(err));
  }, [postid]);

  if (!post) return <p style={{ color: "white" }}>Loading post...</p>;

  const handleSubmit = async () => {
    const formData = new FormData();
    formData.append("title", title);
    formData.append("content", content);
    formData.append("visibility", visibility);

    if (image) {
      formData.append("image", image);
    }

    try {
      const res = await fetch(`http://localhost:8080/post/edit/${postid}`, {
        method: "PUT",
        body: formData,
      });

      if (!res.ok) throw new Error("Failed");

      alert("Post updated!");
      navigate("/profile", { state: { userid } });
    } catch (err) {
      console.error(err);
      alert("Error updating post");
    }
  };

  return (
    <div className="post-container">
      <div className="post-box">
        <h2>Edit Post</h2>

        <input
          type="text"
          value={title}
          onChange={e => setTitle(e.target.value)}
          placeholder="Title"
        />

        <textarea
          value={content}
          onChange={e => setContent(e.target.value)}
          placeholder="Content"
        />

        <select
          value={visibility}
          onChange={e => setVisibility(e.target.value)}
        >
          <option value="PUBLIC">Public</option>
          <option value="FOLLOWERS">Followers</option>
          <option value="FOLLOWING">Following</option>
        </select>

        <input
          type="file"
          onChange={e => setImage(e.target.files[0])}
        />

        <small>Leave empty to keep current image</small>

        <button onClick={handleSubmit}>
          Update Post
        </button>
      </div>
    </div>
  );
}

export default EditPost;