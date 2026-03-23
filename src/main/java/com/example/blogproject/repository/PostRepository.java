package com.example.blogproject.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogproject.model.PostModel;

public interface PostRepository extends JpaRepository<PostModel, UUID> {
    List<PostModel> findByUser_Userid(int userid); // ✅ correct
    List<PostModel> findByTitleContainingIgnoreCase(String title);
}