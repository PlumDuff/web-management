package com.javaweb.service.impl;

import com.javaweb.mapper.DeptMapper;
import com.javaweb.mapper.EmpMapper;
import com.javaweb.pojo.Dept;
import com.javaweb.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    //注入部门管理的Mapper
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        // 在删除部门的同时，关联删除在该部门工作的员工信息
        deptMapper.deleteById(id);
        empMapper.deleteByDeptId(id);

    }


    @Override
    public void add(Dept dept) {
        // 补全内容
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.add(dept);
    }

    @Override
    public void update(Dept dept) {
        // 修改dept的更新时间
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }

    @Override
    public Dept find(Integer id) {
        // 根据id查询
        return deptMapper.find(id);
    }
}
