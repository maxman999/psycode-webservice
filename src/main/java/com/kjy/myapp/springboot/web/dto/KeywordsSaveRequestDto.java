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

    private String keyword1_user;
    private String keyword2_user;
    private String keyword3_user;
    private String useremail;

    @Builder
    public KeywordsSaveRequestDto(String keyword1_user, String keyword2_user, String keyword3_user, String useremail ){
        this.keyword1_user = keyword1_user;
        this.keyword2_user = keyword2_user;
        this.keyword3_user = keyword3_user;
        this.useremail = useremail;
    }

    public Keywords toEntity(){
        User user = User.builder().email(useremail).build();
        return Keywords.builder()
                .keyword1_user(keyword1_user)
                .keyword2_user(keyword2_user)
                .keyword3_user(keyword3_user)
                .user(user)
                .build();
    }
}
