package com.kjy.myapp.springboot.web.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class ScrapRequestDto {
    private String title;
    private String description;
    private String originallink;
    private String pubdate;
}