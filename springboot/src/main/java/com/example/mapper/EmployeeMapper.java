package com.example.mapper;

import com.example.entity.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmployeeMapper {
    // 删除
    @Delete("delete from`employee` where id = #{id}")
    void deleteById(Integer id);

    // 更新
    void updateById(Employee employee);

    // 新增
    void insert(Employee employee);

    List<Employee> selectAll(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee selectById(Integer id);

    @Select("select * from employee where username = #{username}")
    Employee selectByUsername(String username);
    @Select("select * from employee where no = #{no}")
    Employee selectByNo(String no);
}
