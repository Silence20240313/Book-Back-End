package com.example.controller;

import com.example.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/count")
    public Result count(){
        return Result.success(10);
    }
}
