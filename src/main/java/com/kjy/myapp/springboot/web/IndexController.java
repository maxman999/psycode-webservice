package com.kjy.myapp.springboot.web;

import com.kjy.myapp.springboot.config.auth.dto.SessionUser;
import com.kjy.myapp.springboot.service.posts.PostsService;
import com.kjy.myapp.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model){
        System.out.println("1");
        PostsResponseDto dto = postsService.findById(id);
        System.out.println("2");
        model.addAttribute("post", dto);
        System.out.println("3");
        return "posts-update";
    }
}