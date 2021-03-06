package com.kjy.myapp.springboot.web;

import com.kjy.myapp.springboot.service.keywords.KeywordsService;
import com.kjy.myapp.springboot.web.dto.KeywordsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class KeywordApiController {

    private final KeywordsService keywordsService;

    @PostMapping("/api/v1/keywords")
    public Long save(@RequestBody KeywordsSaveRequestDto requestDto) {
        return keywordsService.save(requestDto);
    }

}