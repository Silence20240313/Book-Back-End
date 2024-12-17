package com.example.mapper;

import com.example.entity.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptMapper {
    // 删除
    @Delete("delete from`dept` where id = #{id}")
    void deleteById(Integer id);

    // 更新
    void updateById(Dept dept);

    // 新增
    void insert(Dept dept);

    List<Dept> selectAll(Dept dept);

    @Select("select * from dept where id = #{id}")
    Dept selectById(Integer id);
}
