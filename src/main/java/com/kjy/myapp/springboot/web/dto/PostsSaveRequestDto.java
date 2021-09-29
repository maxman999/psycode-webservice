package com.kjy.myapp.springboot.web.dto;

import com.kjy.myapp.springboot.domain.posts.Posts;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String description;
    private String useremail;
    private String originallink;
    private String pubdate;
    private String summary;

    @Builder
    public PostsSaveRequestDto(String title, String description, String useremail, String originallink, String pubdate, String summary){
        this.title = title;
        this.description = description;
        this.useremail = useremail;
        this.originallink = originallink;
        this.pubdate = pubdate;
        this.summary = summary;
    }

    public Posts toEntity(){
        User user = User.builder().email(useremail).build();
        return Posts.builder()
                .title(title)
                .description(description)
                .originallink(originallink)
                .pubdate(pubdate)
                .summary(summary)
                .user(user)
                .build();
    }


}
