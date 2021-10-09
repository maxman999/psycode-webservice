package com.kjy.myapp.springboot.web;

import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {

    private final String ERROR_404_PAGE_PATH = "view/errors/404";
    private final String ERROR_500_PAGE_PATH = "view/errors/500";
    private final String ERROR_ETC_PAGE_PATH = "view/errors/error";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {
        // 에러 코드 획득.
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        // 에러 코드에 대한 상태 정보
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        if (status != null) {
            // HttpStatus와 비교해 페이지 분기를 나누기 위한 변수
            int statusCode = Integer.valueOf(status.toString());
            // 로그로 상태값을 기록 및 출력
            System.out.println("httpStatus : " + statusCode);
            // 404 error
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // 에러 페이지에 표시할 정보
                model.addAttribute("code", status.toString());
                model.addAttribute("msg", httpStatus.getReasonPhrase());
                model.addAttribute("timestamp", new Date());
                return ERROR_404_PAGE_PATH;
            }
            // 500 error
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_404_PAGE_PATH;
            }
        }
        return ERROR_404_PAGE_PATH;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}



