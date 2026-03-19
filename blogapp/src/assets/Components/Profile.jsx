import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";

function Profile(){

    const location = useLocation();
    const navigate = useNavigate();

    const userid = location.state?.userid;

    const [followers,setFollowers] = useState([]);
    const [following,setFollowing] = useState([]);

    // Load followers & following
    const loadData = () => {

        fetch(`http://localhost:8080/api/follow/followers/${userid}`)
        .then(res=>res.json())
        .then(data=>setFollowers(data))
        .catch(err=>console.log(err));

        fetch(`http://localhost:8080/api/follow/following/${userid}`)
        .then(res=>res.json())
        .then(data=>setFollowing(data))
        .catch(err=>console.log(err));
    };

    useEffect(()=>{

        if(!userid) return;

        loadData();

    },[userid]);


    const followUser = async (id) => {

        await fetch(
        `http://localhost:8080/api/follow/follow?followerId=${userid}&followingId=${id}`,
        {method:"POST"});

        loadData();
    };


    const unfollowUser = async (id) => {

        await fetch(
        `http://localhost:8080/api/follow/unfollow?followerId=${userid}&followingId=${id}`,
        {method:"DELETE"});

        loadData();
    };


    const removeFollower = async (id) => {

        await fetch(
        `http://localhost:8080/api/follow/removeFollower?userId=${userid}&followerId=${id}`,
        {method:"DELETE"});

        loadData();
    };


    const blockUser = async (id) => {

        await fetch(
        `http://localhost:8080/api/follow/block?userId=${userid}&blockedUserId=${id}`,
        {method:"POST"});

        alert("User blocked");

        loadData();
    };


    const goHome = () => {
        navigate("/first",{state:{userid:userid}});
    };

    const searchUsers = () => {
        navigate("/searchusers",{state:{userid:userid}});
    };


    const boxStyle = {
        border:"1px solid #ccc",
        padding:"10px",
        margin:"8px 0",
        borderRadius:"6px",
        display:"flex",
        justifyContent:"space-between",
        alignItems:"center",
        width:"350px",
        background:"#f9f9f9"
    };


    const btnStyle = {
        marginLeft:"5px",
        padding:"4px 8px",
        cursor:"pointer"
    };


    return(

        <div style={{padding:"20px",fontFamily:"Arial"}}>

            <h2>My Profile</h2>

            <button onClick={goHome}>Back To Posts</button>
            <button onClick={searchUsers} style={{marginLeft:"10px"}}>
                Search Users
            </button>

            <hr/>

            <h3>Followers ({followers.length})</h3>

            {followers.length===0 && <p>No followers</p>}

            {followers.map(f=>(
                <div key={f.followId} style={boxStyle}>

                    <span>{f.follower?.username}</span>

                    <div>

                        {f.follower?.userid !== userid && (
                            <button
                            style={btnStyle}
                            onClick={()=>followUser(f.follower.userid)}>
                                Follow
                            </button>
                        )}

                        <button
                        style={btnStyle}
                        onClick={()=>removeFollower(f.follower.userid)}>
                            Remove
                        </button>

                        <button
                        style={btnStyle}
                        onClick={()=>blockUser(f.follower.userid)}>
                            Block
                        </button>

                    </div>

                </div>
            ))}



            <h3>Following ({following.length})</h3>

            {following.length===0 && <p>Not following anyone</p>}

            {following.map(f=>(
                <div key={f.followId} style={boxStyle}>

                    <span>{f.following?.username}</span>

                    <button
                    style={btnStyle}
                    onClick={()=>unfollowUser(f.following.userid)}>
                        Unfollow
                    </button>

                </div>
            ))}

        </div>

    );

}

export default Profile;

