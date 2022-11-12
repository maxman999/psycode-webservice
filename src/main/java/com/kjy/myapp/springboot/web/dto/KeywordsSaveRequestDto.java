package com.kjy.myapp.springboot.web.dto;

import com.kjy.myapp.springboot.domain.keywords.Keywords;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class KeywordsSaveRequestDto {

    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String userEmail;

    @Builder
    public KeywordsSaveRequestDto(String keyword1, String keyword2, String keyword3, String userEmail ){
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.userEmail = userEmail;
    }

    public Keywords toEntity(){
        User user = User.builder().email(userEmail).build();
        return Keywords.builder()
                .keyword1(keyword1)
                .keyword2(keyword2)
                .keyword3(keyword3)
                .user(user)
                .build();
    }
}
