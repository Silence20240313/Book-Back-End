package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;


    // 删除单个数据
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    // 批量删除
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        adminService.deleteBatch(ids);
        return Result.success();
    }
    // 更新
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){
        adminService.update(admin);
        return Result.success();
    }


    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return Result.success();
    }

    // 根据id查询
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Admin admin = adminService.selectById(id);
        return Result.success(admin);
    }

    // 查询员工表的所有数据
    @GetMapping("/selectAll")
    public Result selectAll(Admin admin){
        List<Admin> list = adminService.selectAll(admin);
        return Result.success(list);
    }


    // 分页查询  pageNum:当前页   pageSize：每页的条数
    @GetMapping("/selectPage")
    public Result selectPage(Admin admin,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "2") Integer pageSize){

        PageInfo<Admin> pageInfo = adminService.selectPage(admin,pageNum,pageSize);
        return Result.success(pageInfo);
    }
}
