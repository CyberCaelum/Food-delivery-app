package com.sky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : IndexController
 * @Description : test
 * @Author :  CyberCaelum
 * @Date: 2025-06-26 09:46
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public void redirectToIndex(HttpServletResponse response) throws IOException {
        // 使用重定向而不是转发
        response.sendRedirect("/index.html");
    }
}