package com.example.blogproject.model;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
public class PostModel {

@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID postid;

@ManyToOne
@JoinColumn(name="user_id")
private UserModel user;

private String title;
private String content;
private String imageurl;
private String visibility;

@OneToMany(mappedBy="post")
@JsonIgnore
private List<CommentModel> comments;

}