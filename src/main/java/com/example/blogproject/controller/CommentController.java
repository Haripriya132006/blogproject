package com.example.blogproject.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.blogproject.model.CommentModel;
import com.example.blogproject.service.CommentService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;


    @GetMapping("/post/{postid}")
    public List<CommentModel> getComments(@PathVariable UUID postid){

        return commentService.getComments(postid);
    }


    @PostMapping("/add")
    public String addComment(
            @RequestParam int userid,
            @RequestParam UUID postid,
            @RequestParam String comment){

        return commentService.addComment(userid, postid, comment);
    }

}