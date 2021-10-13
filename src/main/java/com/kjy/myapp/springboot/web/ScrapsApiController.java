package com.kjy.myapp.springboot.web;

import com.kjy.myapp.springboot.service.scraps.ScrapsService;
import com.kjy.myapp.springboot.web.dto.ScrapsResponseDto;
import com.kjy.myapp.springboot.web.dto.ScrapsSaveRequestDto;
import com.kjy.myapp.springboot.web.dto.ScrapsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScrapsApiController {

    private final ScrapsService scrapsService;

    @PostMapping("/api/v1/scraps")
    public Long save(@RequestBody ScrapsSaveRequestDto requestDto) {
        return scrapsService.save(requestDto);
    }

    @PutMapping("/api/v1/scraps/{id}")
    public Long update(@PathVariable Long id, @RequestBody ScrapsUpdateRequestDto requestDto) {
        return scrapsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/scraps/{id}")
    public ScrapsResponseDto findById(@PathVariable Long id) {
        return scrapsService.findById(id);
    }

    @DeleteMapping("/api/v1/scraps/{id}")
    public Long delete(@PathVariable Long id) {
        scrapsService.delete(id);
        return id;
    }

    @PostMapping("/api/v1/scraps/check")
    public boolean check(@RequestBody ScrapsSaveRequestDto requestDto){
        return scrapsService.check(requestDto.getTitle(), requestDto.getUseremail());
    }

}
