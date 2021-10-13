package com.kjy.myapp.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public ScrapsUpdateRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
