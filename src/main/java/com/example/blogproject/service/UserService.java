package com.example.blogproject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.blogproject.handler.Usernotfound;
import com.example.blogproject.model.UserModel;
import com.example.blogproject.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    
    
    public void add(UserModel user){
        userRepository.save(user);
    }
    
    public boolean ifuserexist(String username){
        return userRepository.existsByUsername(username);
    }

    public Optional<String> getusernamefromid(int userid){
        return userRepository.getusernamefromid(userid);
    }

    public String getusernamefromemail(String email){
        return userRepository.getusernamefromemail(email);
    }
    
    public int getuseridfromemail(String email){
        return userRepository.getuseridfromemail(email);
    }
    
    public String checkuserandpass(String useremail,String pass){
        Optional<UserModel> user=userRepository.findByEmailAndPassword(useremail,pass);
        System.out.println("User found="+user.isPresent());
        if(!user.isPresent()){
            throw new Usernotfound("User doesnt exist");
        }
        return ("User is found with email "+useremail+" and the password is correct");

    }
    
    public String finduserwithid(int userid){
        Optional<UserModel> user= userRepository.findById(userid);
        if (!user.isPresent()){
            throw new Usernotfound("user not found");
         }
            
        UserModel userdet=user.get();
        return userdet.getUsername()+" "+userdet.getEmail()+" ";     
 
    }


}
