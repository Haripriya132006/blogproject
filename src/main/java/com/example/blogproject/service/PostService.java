package com.example.blogproject.service;

import java.io.IOException;
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

}