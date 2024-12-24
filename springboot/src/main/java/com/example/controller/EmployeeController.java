package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    // 删除单个数据
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        employeeService.deleteById(id);
        return Result.success();
    }

    // 批量删除数据
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        employeeService.deleteBatch(ids);
        return Result.success();
    }
    // 更新
    @PutMapping("/update")
    public Result update(@RequestBody Employee employee){
        employeeService.update(employee);
        return Result.success();
    }


    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody Employee employee){
        employeeService.add(employee);
        return Result.success();
    }

    // 根据id查询
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);
    }

    // 查询员工表的所有数据
    @GetMapping("/selectAll")
    public Result selectAll(Employee employee){
        List<Employee> list = employeeService.selectAll(employee);
        return Result.success(list);
    }

    // 分页查询  pageNum:当前页   pageSize：每页的条数
    @GetMapping("/selectPage")
    public Result selectPage(Employee employee,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "2") Integer pageSize){

        PageInfo<Employee> pageInfo = employeeService.selectPage(employee,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    // 导出excel
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
       // 1.拿到所有的员工数据
        List<Employee> employeeList = employeeService.selectAll(null);
        // 2.构建ExcelWriter
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 3.设置中文表头
        writer.addHeaderAlias("username","账号");
        writer.addHeaderAlias("name","名称");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("no","工号");
        writer.addHeaderAlias("age","年龄");
        writer.addHeaderAlias("description","个人介绍");
        writer.addHeaderAlias("deptName","部门");
        // 默认情况下，表头Alias的顺序会按照添加的顺序输出，如果想要自定义顺序，可以通过设置forceAlias字段来实现
        writer.setOnlyAlias(true);
        // 4.写出数据到writer
        writer.write(employeeList, true);
        // 5.设置输出的文件的名称以及输出流的头信息
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("员工信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        // 6.写出到输出流并关闭流
        ServletOutputStream os = response.getOutputStream();
        writer.flush(os);
        writer.close();
    }

    // excel导入
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws Exception {
        // 1.拿到输入流，构建reader
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 2.读取excel里面的数据
        reader.addHeaderAlias("账号","username");
        reader.addHeaderAlias("名称","name");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("工号","no");
        reader.addHeaderAlias("年龄","age");
        reader.addHeaderAlias("个人介绍","description");
        reader.addHeaderAlias("部门","deptName");
        List<Employee> employeelist = reader.readAll(Employee.class);
        // 3.写入list数据到数据库
        for (Employee employee : employeelist){
           employeeService.add(employee);
        }
        return Result.success();
    }
}
