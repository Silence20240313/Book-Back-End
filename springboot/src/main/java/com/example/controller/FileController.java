package com.example.controller;

import cn.hutool.core.io.FileUtil;
import com.example.common.Result;
import com.example.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

// 文件相关的接口
@RestController
@RequestMapping("/files")
public class FileController {
    // System.getProperty("user.dir")： 获取到当前项目的根路径
    // 文件上传的目录的路径
    private static final String filePath = System.getProperty("user.dir") + "/files/";
    // 文件上传接口
    @PostMapping("/upload")
    public Result upload(MultipartFile file){  // 文件流的形式接收前端发送过来的文件
        String originalFilename = file.getOriginalFilename();
        if(!FileUtil.isDirectory(filePath)){ // 如果目录不存在需要先创建目录
            FileUtil.mkdir(filePath);// 创建一个files目录
        }
        // 提供文件存储的完整路径
        // 给文件名加一个唯一的标识
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        String realPath = filePath + fileName; // 完整的文件路径
        try{
            FileUtil.writeBytes(file.getBytes(),realPath);
        } catch(Exception e){
            e.printStackTrace();
            throw new CustomException("500","文件上传失败");
        }
        // 返回一个网络链接
        String url = "http://localhost:8080/files/download/" + fileName;
        return Result.success(url);
    }

    // 文件下载
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        OutputStream os = response.getOutputStream();
        String realPath = filePath + fileName;
        // 完整的文件路径 http://localhost:8080/files/download/1734310352785_1.jpeg
        // 获取到文件的字节数组
        byte[] bytes = FileUtil.readBytes(realPath);
        os.write(bytes);
        os.flush();
        os.close();
    }
}
