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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="comment")
@Getter
@Setter
public class CommentModel {

@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID commentid;

@ManyToOne
@JoinColumn(name="user_id")
private UserModel user;

@JsonIgnore
@ManyToOne
@JoinColumn(name="post_id")
private PostModel post;

private String comment;

}