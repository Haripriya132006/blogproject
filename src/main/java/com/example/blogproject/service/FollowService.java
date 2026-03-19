package com.example.blogproject.service;

import com.example.blogproject.model.Followmodel;
import com.example.blogproject.model.UserModel;
import com.example.blogproject.repository.FollowRepository;
import com.example.blogproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;

    public String followUser(int followerId,int followingId){

        UserModel follower=userRepository.findById(followerId).orElseThrow();
        UserModel following=userRepository.findById(followingId).orElseThrow();

        Optional<Followmodel> existing=followRepository.findByFollowerAndFollowing(follower,following);

        if(existing.isPresent()){
            return "Already following";
        }

        Followmodel follow=new Followmodel();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);

        return "Followed successfully";
    }

    public String unfollowUser(int followerId,int followingId){

        UserModel follower=userRepository.findById(followerId).orElseThrow();
        UserModel following=userRepository.findById(followingId).orElseThrow();

        Optional<Followmodel> existing=followRepository.findByFollowerAndFollowing(follower,following);

        if(existing.isEmpty()){
            return "Not following";
        }

        followRepository.delete(existing.get());

        return "Unfollowed successfully";
    }

    public List<Followmodel> getFollowers(int userId){

        UserModel user=userRepository.findById(userId).orElseThrow();

        return followRepository.findByFollowing(user);
    }

    public List<Followmodel> getFollowing(int userId){

        UserModel user=userRepository.findById(userId).orElseThrow();

        return followRepository.findByFollower(user);
    }
    
    public String removeFollower(int userId,int followerId){

    Followmodel follow =
        followRepository.findByFollower_UseridAndFollowing_Userid(followerId,userId);

    if(follow!=null){
        followRepository.delete(follow);
        return "Follower removed";
    }

    return "Follower not found";
}


    public String blockUser(int userId,int blockedUserId){

    Followmodel follow1 =
        followRepository.findByFollower_UseridAndFollowing_Userid(blockedUserId,userId);

    Followmodel follow2 =
        followRepository.findByFollower_UseridAndFollowing_Userid(userId,blockedUserId);

    if(follow1!=null) followRepository.delete(follow1);
    if(follow2!=null) followRepository.delete(follow2);

    return "User blocked";
}

    public boolean isFollowing(int currentUserId,int authorId){
        return followRepository.existsByFollowerUseridAndFollowingUserid(currentUserId, authorId);
    }

    // check if author follows current user
    public boolean isFollower(int currentUserId, int authorId){
        return followRepository.existsByFollowerUseridAndFollowingUserid(authorId, currentUserId);
    }

}