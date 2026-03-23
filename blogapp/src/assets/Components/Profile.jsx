import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import MyPosts from "./MyPosts";
import "../css/Profile.css";

function Profile() {
  const location = useLocation();
  const navigate = useNavigate();

  const userid = location.state?.userid;

  const [followers, setFollowers] = useState([]);
  const [following, setFollowing] = useState([]);

  const loadData = () => {
    if (!userid) return;

    fetch(`http://localhost:8080/api/follow/followers/${userid}`)
      .then((res) => res.json())
      .then((data) => setFollowers(data))
      .catch((err) => console.log(err));

    fetch(`http://localhost:8080/api/follow/following/${userid}`)
      .then((res) => res.json())
      .then((data) => setFollowing(data))
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    loadData();
  }, [userid]);

  const followUser = async (id) => {
    await fetch(
      `http://localhost:8080/api/follow/follow?followerId=${userid}&followingId=${id}`,
      { method: "POST" }
    );
    loadData();
  };

  const unfollowUser = async (id) => {
    await fetch(
      `http://localhost:8080/api/follow/unfollow?followerId=${userid}&followingId=${id}`,
      { method: "DELETE" }
    );
    loadData();
  };

  const goHome = () => {
    navigate("/first", { state: { userid } });
  };

  const searchUsers = () => {
    navigate("/searchusers", { state: { userid } });
  };

  return (
    <>
      {/* 🔵 Navbar (same style as main page) */}
      <div className="navbar">
        <h1>My Profile</h1>

        <div>
          <button onClick={goHome}>Home</button>
          <button onClick={searchUsers}>Search</button>
        </div>
      </div>

      {/* 👥 Followers (inline style) */}
      <div className="profile-info">
        <strong>Followers:</strong>
        {followers.length === 0 && <span> None</span>}

        {followers.map((f) => (
          <span key={f.followId}>
            {f.follower?.username}

            {f.follower?.userid !== userid && (
              <button onClick={() => followUser(f.follower.userid)}>+</button>
            )}
          </span>
        ))}
      </div>

      {/* 👥 Following (inline style) */}
      <div className="profile-info">
        <strong>Following:</strong>
        {following.length === 0 && <span> None</span>}

        {following.map((f) => (
          <span key={f.followId}>
            {f.following?.username}

            <button onClick={() => unfollowUser(f.following.userid)}>x</button>
          </span>
        ))}
      </div>

      {/* 🧠 POSTS (same grid as main page) */}
      {userid && <MyPosts userid={userid} />}
    </>
  );
}

export default Profile;