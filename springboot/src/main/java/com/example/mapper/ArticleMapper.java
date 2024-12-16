package com.example.mapper;

import com.example.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper {
    // 删除
    @Delete("delete from`article` where id = #{id}")
    void deleteById(Integer id);

    // 更新
    void updateById(Article article);

    // 新增
    void insert(Article article);

    List<Article> selectAll(Article article);

    @Select("select * from article where id = #{id}")
    Article selectById(Integer id);


}
