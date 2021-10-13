package com.kjy.myapp.springboot.web.dto;

import com.kjy.myapp.springboot.domain.scraps.Scraps;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Getter;

@Getter
public class ScrapsResponseDto {
    private Long id;
    private String title;
    private String description;
    private User user;

    public ScrapsResponseDto(Scraps entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.user = entity.getUser();
    }


}
