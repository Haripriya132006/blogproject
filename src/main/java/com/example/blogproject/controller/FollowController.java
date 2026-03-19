package com.example.blogproject.controller;

import com.example.blogproject.model.Followmodel;
import com.example.blogproject.service.FollowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin(origins = "http://localhost:5173")
public class FollowController {

    @Autowired
    private FollowService followService;


    // FOLLOW USER
    @PostMapping("/follow")
    public String followUser(@RequestParam int followerId,
                             @RequestParam int followingId) {

        return followService.followUser(followerId, followingId);
    }


    // UNFOLLOW USER
    @DeleteMapping("/unfollow")
    public String unfollowUser(@RequestParam int followerId,
                               @RequestParam int followingId) {

        return followService.unfollowUser(followerId, followingId);
    }


    // GET FOLLOWERS
    @GetMapping("/followers/{userId}")
    public List<Followmodel> getFollowers(@PathVariable int userId) {

        return followService.getFollowers(userId);
    }


    // GET FOLLOWING
    @GetMapping("/following/{userId}")
    public List<Followmodel> getFollowing(@PathVariable int userId) {

        return followService.getFollowing(userId);
    }

    @DeleteMapping("/removeFollower")
public String removeFollower(@RequestParam int userId,
                             @RequestParam int followerId){

    return followService.removeFollower(userId,followerId);
}


    @PostMapping("/block")
public String blockUser(@RequestParam int userId,
                        @RequestParam int blockedUserId){

    return followService.blockUser(userId,blockedUserId);
}
}