package com.kjy.myapp.springboot.web;

import com.kjy.myapp.springboot.config.auth.LoginUser;
import com.kjy.myapp.springboot.config.auth.dto.SessionUser;
import com.kjy.myapp.springboot.service.posts.PostsService;
import com.kjy.myapp.springboot.web.dto.PageRequestDto;
import com.kjy.myapp.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;


    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());
        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("posts/read")
    public String postRead(Model model, PageRequestDto pageRequestDTO){
        model.addAttribute("result", postsService.getListWithPaging(pageRequestDTO));
        model.addAttribute("request", pageRequestDTO);
        return "view/posts/posts-read";
    }

    @GetMapping("posts/save")
    public String postsSave(){
        return "view/posts/posts-save";
    }

    @GetMapping("posts/update/{id}")
    public String postUpdate(@PathVariable Long id, @RequestParam int page, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        model.addAttribute("page", page);
        return "view/posts/posts-update";
    }
}