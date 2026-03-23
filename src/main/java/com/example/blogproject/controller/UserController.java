package com.example.blogproject.controller;

import com.example.blogproject.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogproject.DTO.LoginDTO;
import com.example.blogproject.DTO.ResponseDTO;
import com.example.blogproject.model.UserModel;
import com.example.blogproject.service.UserService;
import com.example.blogproject.util.JwtUtil;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository = userRepository;
    }
    

    @PostMapping("/adduser")
    public String adduser(@RequestBody UserModel user ) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);

        return "user added successfully";
    }
    @PostMapping("/check")
    public ResponseDTO verifyuser(@RequestBody LoginDTO loginDTO){

        UserModel user = userRepository.findByEmail(loginDTO.getEmail())
                .orElse(null);

        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {

            String token = jwtUtil.generateToken(user.getEmail());

            return new ResponseDTO(
                    token,
                    user.getUsername(),
                    user.getUserid()
            );
        }

        return new ResponseDTO("Invalid credentials", "", -1);
    }
    @PostMapping("/userexists/{username}")
    public boolean checkexist(@PathVariable String username){
        return userService.ifuserexist(username.trim());
    }
    @PostMapping("/username/{userid}")
    public Optional<String> usernamefromid(@PathVariable int userid){
        return userService.getusernamefromid(userid);
    }


    @GetMapping("/userdetail/{userid}")
        public String getuserfromid(@PathVariable int userid){
            return userService.finduserwithid(userid);
        }

    @GetMapping("/search")
    public List<UserModel> searchUsers(@RequestParam String name){

        return userRepository.findByUsernameContainingIgnoreCase(name);

    }
}
