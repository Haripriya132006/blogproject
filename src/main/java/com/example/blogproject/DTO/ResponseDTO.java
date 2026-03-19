package com.example.blogproject.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    private String username;
    private int user_id;
    public ResponseDTO(String message,String username,int user_id){
        this.message=message;
        this.username=username;
        this.user_id=user_id;
    }
}
