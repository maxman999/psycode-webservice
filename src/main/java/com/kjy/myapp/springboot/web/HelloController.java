package com.kjy.myapp.springboot.web;


import com.kjy.myapp.springboot.web.dto.HelloResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@Log4j2
public class HelloController {

    @GetMapping("/hello")
    public ModelAndView hello(ModelAndView mv) {
        log.info("hello.................");
        Date time = new Date();
        mv.addObject("date", time);
        mv.setViewName("hello");
        return mv;
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
