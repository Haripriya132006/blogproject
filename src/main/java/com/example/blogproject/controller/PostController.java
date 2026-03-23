package com.example.blogproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.blogproject.DTO.EditPostRequest;
import com.example.blogproject.model.PostModel;
import com.example.blogproject.service.PostService;


@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
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

    
    @GetMapping("/feed/{userid}")
    public List<PostModel> getFeed(@PathVariable int userid){

        return postService.getVisiblePosts(userid);
    }
    @GetMapping("/search/{title}")
    public List<PostModel> searchPostContainingTitle(@PathVariable String title){
        return postService.searchPostByPostContainingTitle(title);
    }
    
    @GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        Path imagePath = Paths.get("uploads").resolve(filename);

        if(!Files.exists(imagePath)){
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);
        String contentType = Files.probeContentType(imagePath); // detects image/png, image/jpeg etc.

        return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(imageBytes);
    }


    // Edit post
    @PutMapping("/edit/{postid}")
    public PostModel editPost(
            @PathVariable UUID postid,
            @ModelAttribute EditPostRequest request
    ) throws IOException {

        return postService.updatePost(
                postid,
                request.getTitle(),
                request.getContent(),
                request.getVisibility(),
                request.getImage()
        );
    }
    
    // Delete post
    @DeleteMapping("/delete/{postid}")
    public String deletePost(@PathVariable UUID postid){
        postService.deletePost(postid);
        return "Post deleted successfully";
    }

    @GetMapping("/user/{userid}")
    public List<PostModel> getMyPosts(@PathVariable int userid) {
        return postService.getPostsByUser(userid); // ensure PostModel has "image" field
    }
    
    @GetMapping("/{postid}")
    public PostModel getPostById(@PathVariable UUID postid) {
        return postService.getPostById(postid);
    }
}