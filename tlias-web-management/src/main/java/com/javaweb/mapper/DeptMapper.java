package com.javaweb.mapper;

import com.javaweb.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    /*
    查询全部数据
     */
    @Select("select * from dept;")
    List<Dept> list();

    // 删除
    @Delete("delete from dept where id = #{id} ;")
    void deleteById(Integer id);

    // 新增
    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void add(Dept dept);
    //更新时间
    @Update("update dept set name = #{name} where id = #{id}")
    void update(Dept dept);

    // 根据Id查询
    @Select("select * from dept where id = #{id};")
    Dept find(Integer id);
}
