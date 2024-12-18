package com.example.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Employee;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.ArticleService;
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
    @Resource
    private ArticleService articleService;

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

    @GetMapping("/lineData")
    public Result getlineData(){
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();// 当前时间
        DateTime start = DateUtil.offsetDay(date,-7);// 起始日期
        List<DateTime> dateTimeList = DateUtil.rangeToList(start,date, DateField.DAY_OF_YEAR);
        // 把DateTime类型的日期转换成字符串类型的日期 ["10月22日"，"10月23日..."]
        List<String> dateStrList = dateTimeList.stream().map(dateTime -> DateUtil.format(dateTime,"MM月dd日"))
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        map.put("date",dateStrList);  // 横轴数据

        List<Integer> countList = new ArrayList<>();
        for (DateTime day : dateTimeList) {
            String dayFormat  = DateUtil.formatDate(day); // 2023-10-22
            // 获取当天所有的发布的文章的数量
           Integer count = articleService.selectCountByDate(dayFormat);
           countList.add(count);
        }
        map.put("count",countList); // 纵轴数据(发布文章的数量)
        return Result.success(map);
    }

    @GetMapping("/pieData")
    public Result getPieData(){
        List<Map<String,Object >> list = new ArrayList<>();
        List<Employee> employeeList = employeeService.selectAll(null);
        Set<String> deptNameSet = employeeList.stream().map(Employee::getDeptName).collect(Collectors.toSet());
        for(String deptName : deptNameSet){
            HashMap<String,Object> map = new HashMap<>();
            map.put("name",deptName);
            // 统计这个部门下面的员工的数量
            long count = employeeList.stream().filter(employee -> employee.getDeptName().equals(deptName)).count();
            map.put("value",count);
            list.add(map);
        }
        return Result.success(list);
    }
}
