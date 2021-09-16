package com.kjy.myapp.springboot.web.dto;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private User user;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.user = entity.getUser();
        this.modifiedDate = entity.getModifiedDate();
    }

}
