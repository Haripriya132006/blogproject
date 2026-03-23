package com.example.blogproject.service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.blogproject.model.PostModel;
import com.example.blogproject.model.UserModel;
import com.example.blogproject.repository.PostRepository;
import com.example.blogproject.repository.UserRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final FollowService followService;
    private final UserRepository userRepository;
    private final Path uploadDir = Paths.get("uploads"); // folder for images

    public PostService(PostRepository postRepository,
                       FollowService followService,
                       UserRepository userRepository){
        this.postRepository = postRepository;
        this.followService = followService;
        this.userRepository = userRepository;
    }

    public String createPost(int userid,
                             String title,
                             String content,
                             String visibility,
                             MultipartFile image) throws IOException {

        String uploadDir = "uploads/";

        String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();

        Path filepath = Paths.get(uploadDir + filename);

        Files.createDirectories(filepath.getParent());

        Files.write(filepath, image.getBytes());

        String imageurl = "http://localhost:8080/images/" + filename;

        UserModel user = userRepository.findById(userid).orElseThrow();

        PostModel post = new PostModel();

        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setImageurl(imageurl);
        post.setVisibility(visibility);

        postRepository.save(post);

        return "Post created";
    }

    public List<PostModel> getAllPosts(){
        return postRepository.findAll();
    }

    public List<PostModel> getVisiblePosts(int currentUserId){

        List<PostModel> allPosts = postRepository.findAll();
        List<PostModel> visiblePosts = new ArrayList<>();

        for(PostModel p : allPosts){

            if(p.getUser().getUserid() == currentUserId){
                visiblePosts.add(p);
            }

            else if("PUBLIC".equals(p.getVisibility())){
                visiblePosts.add(p);
            }

            else if("FOLLOWERS".equals(p.getVisibility())){
                if(followService.isFollower(currentUserId, p.getUser().getUserid())){
                    visiblePosts.add(p);
                }
            }

            else if("FOLLOWING".equals(p.getVisibility())){
                if(followService.isFollowing(currentUserId, p.getUser().getUserid())){
                    visiblePosts.add(p);
                }
            }
        }

        return visiblePosts;
    }

    public List<PostModel> searchPostByPostContainingTitle(String title){
        return postRepository.findByTitleContainingIgnoreCase(title);
    }

    public PostModel updatePost(UUID postid, String title, String content, String visibility, MultipartFile image) throws IOException {
        PostModel post = postRepository.findById(postid)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setTitle(title);
        post.setContent(content);
        post.setVisibility(visibility);

        if (image != null && !image.isEmpty()) {
            try {
                // 1. Delete old image
                String oldImageUrl = post.getImageurl();
                if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    String oldFilename = Paths.get(new URI(oldImageUrl).getPath()).getFileName().toString();
                    Path oldImagePath = Paths.get("uploads").resolve(oldFilename);
                    if (Files.exists(oldImagePath)) {
                        Files.delete(oldImagePath);
                    }
                }

                // 2. Save new image
                String newFilename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path uploadPath = Paths.get("uploads").resolve(newFilename);
                Files.createDirectories(uploadPath.getParent());
                Files.write(uploadPath, image.getBytes());

                // 3. Update post URL
                post.setImageurl("http://localhost:8080/images/" + newFilename);
            } catch (Exception e) {
                e.printStackTrace();
                // optionally throw RuntimeException if you want the edit to fail on image errors
            }
        }

        return postRepository.save(post);
    }
        

    public void deletePost(UUID postid) {
            PostModel post = postRepository.findById(postid)
                    .orElseThrow(() -> new RuntimeException("Post not found"));

            try {
                // Get the image URL from DB
                String imageUrl = post.getImageurl(); // e.g., http://localhost:8080/images/xyz.jpg

                if (imageUrl != null && !imageUrl.isEmpty()) {
                    // Extract filename from URL
                    String filename = Paths.get(new URI(imageUrl).getPath()).getFileName().toString();

                    // Construct local path
                    Path imagePath = Paths.get("uploads").resolve(filename);

                    // Delete file if exists
                    if (Files.exists(imagePath)) {
                        Files.delete(imagePath);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log error, but still continue with DB deletion
            }

            // Delete post from DB
            postRepository.delete(post);
        }
    
   
    public List<PostModel> getPostsByUser(int userid) {
        return postRepository.findByUser_Userid(userid);
    }

    public PostModel getPostById(UUID postid) {
        return postRepository.findById(postid)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
    
