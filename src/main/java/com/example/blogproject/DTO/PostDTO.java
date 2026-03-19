package com.example.blogproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private int userid;
    private String imageurl;
    private String title;
    private String content;

    public PostDTO(){
    }
    public PostDTO(int userid,String imageurl,String title,String content){
        this.userid=userid;
        this.imageurl=imageurl;
        this.title=title;
        this.content=content;
    }
}
