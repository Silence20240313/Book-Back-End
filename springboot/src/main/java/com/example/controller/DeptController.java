package com.example.controller;

import com.example.common.Result;
import com.example.entity.Dept;
import com.example.service.DeptService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    // 删除单个数据
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        deptService.deleteById(id);
        return Result.success();
    }

    // 批量删除数据
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        deptService.deleteBatch(ids);
        return Result.success();
    }
    // 更新
    @PutMapping("/update")
    public Result update(@RequestBody Dept dept){
        deptService.update(dept);
        return Result.success();
    }


    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody Dept dept){
        deptService.add(dept);
        return Result.success();
    }

    // 根据id查询
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Dept dept = deptService.selectById(id);
        return Result.success(dept);
    }

    // 查询员工表的所有数据
    @GetMapping("/selectAll")
    public Result selectAll(Dept dept){
        List<Dept> list = deptService.selectAll(dept);
        return Result.success(list);
    }

    // 分页查询  pageNum:当前页   pageSize：每页的条数
    @GetMapping("/selectPage")
    public Result selectPage(Dept dept,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "2") Integer pageSize){

        PageInfo<Dept> pageInfo = deptService.selectPage(dept,pageNum,pageSize);
        return Result.success(pageInfo);
    }
}
