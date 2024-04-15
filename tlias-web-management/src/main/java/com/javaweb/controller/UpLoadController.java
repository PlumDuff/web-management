package com.javaweb.controller;


import com.javaweb.pojo.Result;
import com.javaweb.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@RestController
public class UpLoadController {
    @Autowired
    private AliOSSUtils aliOSSUtils;
/*    @PostMapping("/upload")
    public Result upload(String username, Integer id, MultipartFile image)throws Exception{
        log.info("上传信息：{},{},{}",username,id,image);
        // 1.获取原始文件名称
        String originalFileName = image.getOriginalFilename();
        // 截取文件类型
        String newFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.indexOf('.'));
        // 将文件保存再本地
        image.transferTo(new File("F:/images/"+newFileName));
        return Result.success();
    }*/

    @PostMapping("/upload")
    public Result upload( MultipartFile image)throws Exception{
        log.info("上传信息：{}",image.getOriginalFilename());
        // 调用阿里云上传文件
        String url = aliOSSUtils.upload(image);
        log.info("文件上传完成，文件访问的url：{}",url);
        return Result.success(url);
    }
}
