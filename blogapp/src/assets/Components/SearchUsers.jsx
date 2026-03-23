import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../css/Searchusers.css";

function SearchUsers(){

    const location = useLocation();
    const navigate = useNavigate();

    const userid = location.state?.userid;

    const [search,setSearch] = useState("");
    const [users,setUsers] = useState([]);

    const searchUsers = async () => {

        try{

            const response = await fetch(
                "http://localhost:8080/user/search?name="+search
            );

            const data = await response.json();

            setUsers(data);

        }
        catch(error){

            console.log("Search error:",error);

        }

    }


    const followUser = async (id) => {

        try{

            const response = await fetch(
            `http://localhost:8080/api/follow/follow?followerId=${userid}&followingId=${id}`,
            {
                method:"POST"
            });

            const data = await response.text();

            alert(data);

        }
        catch(error){

            console.log("Follow error:",error);

        }

    }


    const unfollowUser = async (id) => {

        try{

            const response = await fetch(
            `http://localhost:8080/api/follow/unfollow?followerId=${userid}&followingId=${id}`,
            {
                method:"DELETE"
            });

            const data = await response.text();

            alert(data);

        }
        catch(error){

            console.log("Unfollow error:",error);

        }

    }


    const goProfile = () => {

        navigate("/profile",{state:{userid:userid}});

    }


    return (
    <div>
        {/* 🔵 Navbar */}
        <div className="navbar">
        <h1>Search Users</h1>

        <div>
            <button onClick={goProfile}>Profile</button>
        </div>
        </div>

        {/* 🔍 Search bar */}
        <div className="search-bar">
        <input
            placeholder="Search username..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
        />

        <button onClick={searchUsers}>Search</button>
        </div>

        {/* 👥 Results */}
        <div className="users-container">
        {users.length === 0 && <p>No users found</p>}

        {users.map((user) => (
            <div key={user.userid} className="user-card">
            <h3>{user.username}</h3>

            <div className="user-actions">
                <button onClick={() => followUser(user.userid)}>
                Follow
                </button>

                <button 
                className="danger-btn"
                onClick={() => unfollowUser(user.userid)}
                >
                Unfollow
                </button>
            </div>
            </div>
        ))}
        </div>
    </div>
    );

}

export default SearchUsers;