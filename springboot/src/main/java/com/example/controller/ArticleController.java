package com.example.controller;

import com.example.common.Result;
import com.example.entity.Article;
import com.example.service.ArticleService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    // 删除单个数据
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        articleService.deleteById(id);
        return Result.success();
    }





    // 批量删除数据
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        articleService.deleteBatch(ids);
        return Result.success();
    }
    // 更新
    @PutMapping("/update")
    public Result update(@RequestBody Article article){
        articleService.update(article);
        return Result.success();
    }





    // 新增
    @PostMapping("/add")
    public Result add(@RequestBody Article article){
        articleService.add(article);
        return Result.success();
    }

    // 根据id查询
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Article article = articleService.selectById(id);
        return Result.success(article);
    }

    // 查询员工表的所有数据
    @GetMapping("/selectAll")
    public Result selectAll(Article article){
        List<Article> list = articleService.selectAll(article);
        return Result.success(list);
    }

    // 分页查询  pageNum:当前页   pageSize：每页的条数
    @GetMapping("/selectPage")
    public Result selectPage(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "2") Integer pageSize){

        PageInfo<Article> pageInfo = articleService.selectPage(article,pageNum,pageSize);
        return Result.success(pageInfo);
    }
}
