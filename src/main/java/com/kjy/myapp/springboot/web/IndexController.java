package com.kjy.myapp.springboot.web;

import com.kjy.myapp.springboot.config.auth.LoginUser;
import com.kjy.myapp.springboot.config.auth.dto.SessionUser;
import com.kjy.myapp.springboot.service.keywords.KeywordsService;
import com.kjy.myapp.springboot.service.posts.PostsService;
import com.kjy.myapp.springboot.web.dto.PageRequestDto;
import com.kjy.myapp.springboot.web.dto.PostsResponseDto;
import com.kjy.myapp.springboot.web.dto.ScrapRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final KeywordsService keywordsService;


    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "view/login";
    }


    @GetMapping("posts/read")
    public String postRead(Model model, PageRequestDto pageRequestDTO, @LoginUser SessionUser user) {
        model.addAttribute("result", postsService.getListWithPaging(pageRequestDTO, user.getEmail()));
        model.addAttribute("request", pageRequestDTO);
        return "view/posts/posts-read";
    }

    // custom news 스크랩을 위한 위한 url
    @GetMapping("posts/save")
    public String postsSaveGet(Model model, @LoginUser SessionUser user) {
        model.addAttribute("userEmail", user.getEmail());
        return "view/posts/posts-save";
    }

    // api를 통한 news 스크랩을 위한 url
    @PostMapping("posts/save")
    public String postsSavePost(Model model, @LoginUser SessionUser user, ScrapRequestDto scrapRequestDto) {
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("news", scrapRequestDto);
        return "view/posts/posts-save";
    }

    @GetMapping("posts/update/{id}")
    public String postUpdate(@PathVariable Long id, @RequestParam int page, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        model.addAttribute("page", page);
        return "view/posts/posts-update";
    }

    @GetMapping("news")
    public String news(Model model, @LoginUser SessionUser user) {
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("keywords", keywordsService.getKeywordList(user.getEmail()));
        System.out.println(keywordsService.getKeywordList(user.getEmail()));
        return "view/news/read";
    }

    @GetMapping("portfolio")
    public String portfolio() {
        return "view/portfolio/read";
    }

    @GetMapping("portfolio/pdf")
    public String pdfRead(@RequestParam String target, Model model) {
        System.out.println("target : " +  target);
        model.addAttribute("target", target);
        return "view/portfolio/pdf-read";
    }



}