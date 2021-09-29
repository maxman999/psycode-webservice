package com.kjy.myapp.springboot.web;


import com.kjy.myapp.springboot.config.auth.dto.SessionUser;
import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import com.kjy.myapp.springboot.web.dto.HelloResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    @GetMapping("/guestTest")
    public ModelAndView guestTest(ModelAndView mv, HttpSession session){
        User user = User.builder().name("김지용").email("rlagkfqo55@gmail.com").picture("https://lh3.googleusercontent.com/a/AATXAJxPIHlZylPCjTFWkDavAw7yAKbqxw1INPVOE9Wy=s96-c").role(Role.USER).build();
        session.setAttribute("user", new SessionUser(user));
        mv.setViewName("index");
        return mv;
    }





//    @ResponseBody
//    @RequestMapping(value = "/getKeyword", method = RequestMethod.GET)
//    public String getKeywordFromFlask() {
//        String url = "http://127.0.0.1:5000/getKeyword";
//        String result = excutePost(url);
//        return result.toString();
//    }
//
//    public String excutePost(String url) {
//        HttpURLConnection conn = null;
//        String result = "";
//        try {
//            conn = (HttpURLConnection) new URL(url).openConnection();
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                result += line + "\n";
//            }
//            br.close();
//            System.out.println("통신 결과 : " + result);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }
//        return result;
//    }

}
