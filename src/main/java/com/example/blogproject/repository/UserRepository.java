package com.example.blogproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.blogproject.model.UserModel;
import java.util.List;


public interface UserRepository extends JpaRepository<UserModel,Integer> {
    boolean existsByUsername(String username);
    @Query(
        nativeQuery = true,value = "Select username from users where userid=:userid")
    Optional<String> getusernamefromid(@Param("userid") int userid);

    @Query(nativeQuery = true,value="Select username from users where email=:email")
    String getusernamefromemail(@Param("email") String email);

    @Query(nativeQuery = true,value="Select userid from users where email=:email")
    int getuseridfromemail(@Param("email") String email);

    Optional<UserModel> findById(int userid);
    

    Optional<UserModel> findByEmailAndPassword(String email, String password);

    List<UserModel> findByUsernameContainingIgnoreCase(String name);

    Optional<UserModel> findByEmail(String email);
        
}
