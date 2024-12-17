package com.example.service;

import cn.hutool.core.util.StrUtil;
import com.example.entity.Account;
import com.example.entity.Dept;
import com.example.exception.CustomException;
import com.example.mapper.DeptMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {
    @Resource
    private DeptMapper deptMapper;

    // 删除
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    // 更新
    public void update(Dept dept) {
        deptMapper.updateById(dept);
    }


    // 新增
    public void add(Dept dept) {
        deptMapper.insert(dept);
    }


    public List<Dept> selectAll(Dept dept) {
        return deptMapper.selectAll(dept);
    }

    public Dept selectById(Integer id) {
        return deptMapper.selectById(id);
    }

    public PageInfo<Dept> selectPage(Dept dept,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Dept> list = deptMapper.selectAll(dept);
        return PageInfo.of(list);
    }

    // 批量删除
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
             this.deleteById(id);
        }
    }
}
