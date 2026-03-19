package com.example.blogproject.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogproject.model.CommentModel;
import com.example.blogproject.model.PostModel;
import com.example.blogproject.model.UserModel;
import com.example.blogproject.repository.CommentRepository;
import com.example.blogproject.repository.PostRepository;
import com.example.blogproject.repository.UserRepository;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;


    public List<CommentModel> getComments(UUID postid){
        return commentRepository.findByPost_Postid(postid);
    }


    public String addComment(int userid, UUID postid, String comment){

        UserModel user = userRepository.findById(userid).orElse(null);
        PostModel post = postRepository.findById(postid).orElse(null);

        if(user == null || post == null){
            return "User or Post not found";
        }

        CommentModel c = new CommentModel();
        c.setUser(user);
        c.setPost(post);
        c.setComment(comment);

        commentRepository.save(c);

        return "Comment added";
    }

}