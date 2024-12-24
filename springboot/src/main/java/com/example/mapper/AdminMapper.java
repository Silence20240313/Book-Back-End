package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {
    // 删除
    @Delete("delete from`admin` where id = #{id}")
    void deleteById(Integer id);

    // 更新
    void updateById(Admin admin);

    // 新增
    void insert(Admin admin);

    List<Admin> selectAll(Admin admin);

    @Select("select * from admin where id = #{id}")
    Admin selectById(Integer id);

    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);
}
