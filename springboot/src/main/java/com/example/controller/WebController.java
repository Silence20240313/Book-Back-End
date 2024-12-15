package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Employee;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {
    @Resource
    private EmployeeService employeeService;
    @Resource
    private AdminService adminService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    // 管理员/员工登录
    @PostMapping("/login")
    public Result login(@RequestBody Account account){
        Account result = null;
        if("ADMIN".equals(account.getRole())){  // 管理员登录
            result = adminService.login(account);
        }else if ("EMP".equals(account.getRole())) {
            result = employeeService.login(account);
        }else {
            throw new CustomException("500","非法输入");
        }
        return Result.success(result);
    }

    // 员工注册
    @PostMapping("/register")
    public Result register(@RequestBody Employee employee){
        employeeService.register(employee);
        return Result.success();
    }

    // 修改密码
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account){
        if("ADMIN".equals(account.getRole())){  // 管理员登录
           adminService.updatePassword(account);
        }else if ("EMP".equals(account.getRole())) {
           employeeService.updatePassword(account);
        }else {
            throw new CustomException("500","非法输入");
        }
        return Result.success();
    }
}
