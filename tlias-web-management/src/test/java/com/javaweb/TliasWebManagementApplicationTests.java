package com.javaweb;

import com.javaweb.controller.DeptController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class TliasWebManagementApplicationTests {
    @Autowired
     private ApplicationContext applicationContext; // IOC容器对象

    /*
    测试jwt令牌的生成
    * */
    Map<String,Object> claims = new HashMap<>();
    @Test
    public void testGenJwt() {
        claims.put("id",1);
        claims.put("name","Tom");
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256,"javaweb")  //签名算法;
                    .setClaims(claims)      // 自定义内容
                    .setExpiration(new Date(System.currentTimeMillis()+3600*1000))  // 设置有效期为1h
                    .compact();
        System.out.println(jwt);
    }
    @Test
    public void parserJwt(){
        Claims claims1 = Jwts.parser().setSigningKey("javaweb")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiVG9tIiwiaWQiOjEsImV4cCI6MTcxMjkxMTUyMn0.mddZT_8CGra3AOMT6EPrT-Ms3XYNwkstHks0RXCR0AU")
                .getBody();
        System.out.println(claims1);
    }
    @Test
    public void testGetBean(){
        // 根据bean名称获取
        DeptController bean1 = (DeptController) applicationContext.getBean("deptController");
        System.out.println(bean1);

        // 根据bean的类型获取
        DeptController bean2 = applicationContext.getBean(DeptController.class);
        System.out.println(bean2);
        // 根据bean的名称及类型
        DeptController bean3 = applicationContext.getBean("deptController",DeptController.class);
        System.out.println(bean3);
    }

}
