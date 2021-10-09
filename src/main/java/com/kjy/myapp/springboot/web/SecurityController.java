package com.kjy.myapp.springboot.web;
import com.kjy.myapp.springboot.config.auth.dto.SessionUser;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class SecurityController {

    private final UserRepository userRepository;

    @RequestMapping(value = "/guest")
    public String guestLogin (HttpSession httpSession){
        User user = userRepository.findByEmail("GUEST").get();
        httpSession.setAttribute("user", new SessionUser(user));
        System.out.println("GUEST 권한으로 로그인");
        return "index";
    }

}
