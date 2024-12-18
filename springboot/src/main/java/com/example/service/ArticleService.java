package com.example.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.entity.Account;
import com.example.entity.Article;
import com.example.exception.CustomException;
import com.example.mapper.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    // 删除
    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    // 更新
    public void update(Article article) {
        articleMapper.updateById(article);
    }


    // 新增
    public void add(Article article) {
        article.setTime(DateUtil.now()); // 获取当下时间的字符串
        articleMapper.insert(article);
    }


    public List<Article> selectAll(Article article) {
        return articleMapper.selectAll(article);
    }

    public Article selectById(Integer id) {
        return articleMapper.selectById(id);
    }

    public PageInfo<Article> selectPage(Article article,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Article> list = articleMapper.selectAll(article);
        return PageInfo.of(list);
    }

    // 批量删除
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
             this.deleteById(id);
        }
    }

    public Integer selectCountByDate(String date) {
        return articleMapper.selectCountByDate(date);
    }
}
