package com.kjy.myapp.springboot.web;

import com.kjy.myapp.springboot.config.auth.LoginUser;
import com.kjy.myapp.springboot.config.auth.dto.SessionUser;
import com.kjy.myapp.springboot.service.keywords.KeywordsService;
import com.kjy.myapp.springboot.service.posts.PostsService;
import com.kjy.myapp.springboot.web.dto.PageRequestDto;
import com.kjy.myapp.springboot.web.dto.PostsResponseDto;
import com.kjy.myapp.springboot.web.dto.ScrapRequestDto;
import lombok.RequiredArgsConstructor;
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
        if (user != null)
            model.addAttribute("userName", user.getName());
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "view/login";
    }

    @GetMapping("portfolio")
    public String portfolio() {
        return "view/portfolio/read";
    }

    @GetMapping("portfolio/pdf")
    public String pdfRead(@RequestParam String target, Model model) {
        model.addAttribute("target", target);
        return "view/portfolio/pdf-read";
    }

    @GetMapping("news")
    public String news(Model model, @LoginUser SessionUser user) {
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("keywords", keywordsService.getKeywordList(user.getEmail()));
        return "view/news/read";
    }

    @GetMapping("posts/read")
    public String postRead(Model model, PageRequestDto pageRequestDTO, @LoginUser SessionUser user) {
        model.addAttribute("result", postsService.getListWithPaging(pageRequestDTO, user.getEmail()));
        model.addAttribute("request", pageRequestDTO);
        return "view/posts/posts-read";
    }

    // 유저가 직접 news 스크랩을 하는 경우 호출하는 url
    @GetMapping("posts/save")
    public String postsSaveGet(Model model, @LoginUser SessionUser user) {
        model.addAttribute("userEmail", user.getEmail());
        return "view/posts/posts-save";
    }

    // 네이버 기사를 스크랩을 하는 경우 호출하는 url
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

}