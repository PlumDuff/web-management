package com.javaweb.mapper;

import com.javaweb.pojo.Emp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
@Mapper
public interface EmpMapper {
    // 获取总记录数 total
    @Select("select count(*) from emp;")
    public Long count();
    // 获取列表数据
   /* @Select("select * from emp limit #{start},#{pageSize};")
    public List<Emp> page(Integer start, Integer pageSize);*/

    /* 上述步骤过于繁琐，使用pagehelper分页助手*/
   /* @Select("select * from emp")
    public List<Emp> list();*/

    // 条件查询,使用xml文件
    @Mapper
    public  List<Emp> page(String name,  Short gender, LocalDate begin,  LocalDate end);

    void delete(List<Integer> ids);


    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
                    "VALUES(#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime}) ")
    void insert(Emp emp);
    @Select("select * from emp where id = #{id};")
    Emp getById(Integer id);
    // 更新员工信息
    void update(Emp emp);
    // 根据用户名和密码查询用户
    @Select("select * from emp where username=#{username} and password=#{password}")
    Emp getByUsernameAndPassword(Emp emp);


    // 根据部门ID删除该部门下的员工
    @Select("select * from emp where dept_id = #{deptId}")
    public void deleteByDeptId(Integer deptId);
}
