package com.example.blogproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@Table(name="follow")
public class Followmodel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID followId;

    @ManyToOne
    @JoinColumn(name="follower_id")
    private UserModel follower;

    @ManyToOne
    @JoinColumn(name="following_id")
    private UserModel following;

}