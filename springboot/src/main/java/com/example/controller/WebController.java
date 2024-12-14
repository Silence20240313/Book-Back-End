package com.example.controller;

import com.example.common.Result;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    // 员工登录
    @PostMapping("/login")
    public Result login(@RequestBody Employee employee){
        Employee dbEmployee = employeeService.login(employee);
        return Result.success(dbEmployee);
    }

    // 员工注册
    @PostMapping("/register")
    public Result register(@RequestBody Employee employee){
        employeeService.register(employee);
        return Result.success();
    }
}
