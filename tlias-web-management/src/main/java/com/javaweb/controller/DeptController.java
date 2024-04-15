package com.javaweb.controller;

import com.javaweb.anno.Log;
import com.javaweb.pojo.Dept;
import com.javaweb.pojo.Result;
import com.javaweb.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {
    // 控制层数据最终注入Service
    @Autowired
    private DeptService deptService;

    // 通过日志获取信息
    //private static Logger log = LoggerFactory.getLogger(DeptController.class);

    // 查询部门信息
    //@RequestMapping(value = "/depts",method = RequestMethod.GET)    // 指定请求方式为Get
    @GetMapping
    public Result list(){
        log.info("查询全部部门数据");
        // 调用Service查询数据,接收来自服务层的数据
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    // 根据ID删除部门
    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门:{}",id);
        deptService.deleteById(id);
        return Result.success();
    }

    // 新增部门
    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门：{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    // 修改部门
    @Log
    @PutMapping
    public  Result update(@RequestBody Dept dept){
        log.info("修改部门：{}",dept);
        deptService.update(dept);
        return Result.success();
    }

    // 根据id查询
    @GetMapping("/{id}")
    public Result find(@PathVariable Integer id){
        log.info("根据id查询：{}",id);
        Dept dept = deptService.find(id);
        return Result.success(dept);
    }
}
