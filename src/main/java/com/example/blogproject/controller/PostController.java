package com.example.blogproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.blogproject.model.PostModel;
import com.example.blogproject.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/create")
    public String createPost(
            @RequestParam int userid,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String visibility,
            @RequestParam MultipartFile image
    ) throws IOException {

        return postService.createPost(userid, title, content, visibility, image);
    }

    @GetMapping("/all")
    public List<PostModel> getAllPosts(){
        return postService.getAllPosts();
    }
    
    @GetMapping("/feed/{userid}")
    public List<PostModel> getFeed(@PathVariable int userid){

        return postService.getVisiblePosts(userid);
    }
    @GetMapping("/search/{title}")
    public List<PostModel> searchPostContainingTitle(@PathVariable String title){
        return postService.searchPostByPostContainingTitle(title);
    }
    

}