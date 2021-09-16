package com.kjy.myapp.springboot.web.dto;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity(){
        User user = User.builder().email(author).build();
        return Posts.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }


}
