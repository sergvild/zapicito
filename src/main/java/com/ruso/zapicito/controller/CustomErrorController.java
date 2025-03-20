package com.ruso.zapicito.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {


    @GetMapping("/error")
    String error(HttpServletRequest request) {
        return "<h1>Error occurred</h1>";
    }

}
