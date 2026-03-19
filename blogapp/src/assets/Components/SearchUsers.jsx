import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

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


    return(

        <div>

            <h2>Search Users</h2>

            <button onClick={goProfile}>
                Back To Profile
            </button>

            <br/><br/>

            <input
            placeholder="Search username"
            value={search}
            onChange={(e)=>setSearch(e.target.value)}
            />

            <button onClick={searchUsers}>
                Search
            </button>

            <hr/>

            {users.length === 0 && <p>No users found</p>}

            {users.map(user => (

                <div key={user.userid}
                style={{
                    border:"1px solid black",
                    margin:"10px",
                    padding:"10px"
                }}>

                    <b>{user.username}</b>

                    <br/><br/>

                    <button
                    onClick={()=>followUser(user.userid)}
                    >
                    Follow
                    </button>

                    <button
                    onClick={()=>unfollowUser(user.userid)}
                    >
                    Unfollow
                    </button>

                </div>

            ))}

        </div>

    )

}

export default SearchUsers;