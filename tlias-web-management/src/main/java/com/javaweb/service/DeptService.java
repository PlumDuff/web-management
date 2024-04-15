package com.javaweb.service;

import com.javaweb.pojo.Dept;

import java.util.List;

public interface DeptService {
    // 查找全部部门数据
    List<Dept> list();

    // 删除指定id
    void deleteById(Integer id);
    // 新增部门
    void add(Dept dept);
    // 修改部门
    void update(Dept dept);
    // 根据id查询
    Dept find(Integer id);
}
