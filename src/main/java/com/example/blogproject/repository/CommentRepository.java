package com.example.blogproject.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogproject.model.CommentModel;

public interface CommentRepository extends JpaRepository<CommentModel, UUID>{

    List<CommentModel> findByPost_Postid(UUID postid);

}
