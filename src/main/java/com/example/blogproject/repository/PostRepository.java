package com.example.blogproject.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogproject.model.PostModel;
import com.example.blogproject.model.UserModel;

import java.util.List;


public interface PostRepository extends JpaRepository<PostModel, UUID> {
    List<PostModel> findByTitleContainingIgnoreCase(String title);
}