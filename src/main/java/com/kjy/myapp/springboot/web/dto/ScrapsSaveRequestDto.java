package com.kjy.myapp.springboot.web.dto;

import com.kjy.myapp.springboot.domain.scraps.Scraps;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapsSaveRequestDto {
    private String title;
    private String description;
    private String useremail;
    private String originallink;
    private String pubdate;
    private String summary;

    @Builder
    public ScrapsSaveRequestDto(String title, String description, String useremail, String originallink, String pubdate, String summary){
        this.title = title;
        this.description = description;
        this.useremail = useremail;
        this.originallink = originallink;
        this.pubdate = pubdate;
        this.summary = summary;
    }

    public Scraps toEntity(){
        User user = User.builder().email(useremail).build();
        return Scraps.builder()
                .title(title)
                .description(description)
                .originallink(originallink)
                .pubdate(pubdate)
                .summary(summary)
                .user(user)
                .build();
    }


}
