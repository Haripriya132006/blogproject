package com.example.blogproject.repository;

import com.example.blogproject.model.Followmodel;
import com.example.blogproject.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Followmodel, UUID> {

    Optional<Followmodel> findByFollowerAndFollowing(UserModel follower, UserModel following);

    List<Followmodel> findByFollower(UserModel follower);

    List<Followmodel> findByFollowing(UserModel following);
        // Find a specific follow relation
    Followmodel findByFollower_UseridAndFollowing_Userid(int followerId, int followingId);

    // Get all followers of a user
    List<Followmodel> findByFollowing_Userid(int userId);

    // Get all users someone is following
    List<Followmodel> findByFollower_Userid(int userId);

    boolean existsByFollowerUseridAndFollowingUserid(int followerId, int followingId);

    
}