package com.kjy.myapp.springboot.web;

import com.kjy.myapp.springboot.config.auth.LoginUser;
import com.kjy.myapp.springboot.config.auth.dto.SessionUser;
import com.kjy.myapp.springboot.service.keywords.KeywordsService;
import com.kjy.myapp.springboot.service.scraps.ScrapsService;
import com.kjy.myapp.springboot.web.dto.PageRequestDto;
import com.kjy.myapp.springboot.web.dto.ScrapsResponseDto;
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

    private final ScrapsService scrapsService;
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

    @GetMapping("scraps/read")
    public String scrapRead(Model model, PageRequestDto pageRequestDTO, @LoginUser SessionUser user) {
        model.addAttribute("result", scrapsService.getListWithPaging(pageRequestDTO, user.getEmail()));
        model.addAttribute("request", pageRequestDTO);
        return "view/scraps/scraps-read";
    }

    // 유저가 직접 news 스크랩을 하는 경우 호출하는 url
    @GetMapping("scraps/save")
    public String scrapsSaveGet(Model model, @LoginUser SessionUser user) {
        model.addAttribute("userEmail", user.getEmail());
        return "view/scraps/scraps-save";
    }

    // 네이버 기사를 스크랩을 하는 경우 호출하는 url
    @PostMapping("scraps/save")
    public String scrapsSavePost(Model model, @LoginUser SessionUser user, ScrapRequestDto scrapRequestDto) {
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("news", scrapRequestDto);
        return "view/scraps/scraps-save";
    }

    @GetMapping("scraps/update/{id}")
    public String postUpdate(@PathVariable Long id, @RequestParam int page, Model model) {
        ScrapsResponseDto dto = scrapsService.findById(id);
        model.addAttribute("scrap", dto);
        model.addAttribute("page", page);
        return "view/scraps/scraps-update";
    }

    @GetMapping("tsTest")
    public String tsTest(){
        return "view/types/ts-test";
    }

}