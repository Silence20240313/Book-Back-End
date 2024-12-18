package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Employee;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/barData")
    public Result getBarData(){
        Map<String,Object> map = new HashMap<>();
        List<Employee> employeeList = employeeService.selectAll(null);
        Set<String> deptNameSet = employeeList.stream().map(Employee::getDeptName).collect(Collectors.toSet());
        map.put("dept",deptNameSet);  // 横轴数据
        List<Long> countList = new ArrayList<>();
        for(String deptName : deptNameSet){
            // 统计这个部门下面的员工的数量
            long count = employeeList.stream().filter(employee -> employee.getDeptName().equals(deptName)).count();
            countList.add(count);
        }
        map.put("count",countList); // 纵轴数据(员工数量数据)
        return Result.success(map);
    }
}
