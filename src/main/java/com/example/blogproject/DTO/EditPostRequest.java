package com.example.blogproject.DTO;

import org.springframework.web.multipart.MultipartFile;

public class EditPostRequest {

    private String title;
    private String content;
    private String visibility;
    private MultipartFile image; // can be null

    // Getters & Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }
}