package com.kjy.myapp.springboot.web.dto;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private User user;
    private String description;
    private LocalDateTime modifiedDate;
    private String originallink;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.user = entity.getUser();
        this.description =entity.getDescription();
        this.originallink =entity.getOriginallink();
        this.modifiedDate = entity.getModifiedDate();
    }

}
