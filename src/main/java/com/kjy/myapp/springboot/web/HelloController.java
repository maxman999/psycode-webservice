package com.kjy.myapp.springboot.web;


import com.kjy.myapp.springboot.web.dto.HelloResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@PropertySource("classpath:application-oauth.properties")
@RestController
@Log4j2
public class HelloController {

    @Value("${newsApiId}")
    private String newsApiId;
    @Value("${newsApiSecret}")
    private String newsApiSecret;

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

    @GetMapping("/paramTest")
    public ModelAndView paramTest(ModelAndView mv){
        System.out.println(newsApiId);
        System.out.println(newsApiSecret);
        mv.setViewName("hello");
        return mv;
    }

}
