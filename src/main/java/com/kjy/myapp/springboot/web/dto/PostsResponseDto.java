package com.kjy.myapp.springboot.web.dto;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String description;
    private User user;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.user = entity.getUser();
    }


}
