package com.example.blogproject.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
public class UserModel {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int userid;

private String username;
private String email;
private String password;

@OneToMany(mappedBy="user")
@JsonIgnore
private List<PostModel> posts;

@JsonIgnore
@OneToMany(mappedBy = "follower")
private List<Followmodel> followingList;

@JsonIgnore
@OneToMany(mappedBy = "following")
private List<Followmodel> followersList;

}