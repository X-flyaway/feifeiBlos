package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class FileController {

    @Value("${file.upload.path}")
    private String uploadPath;

    @PostMapping("/upload/image")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + ext;
            // 保存路径
            Path path = Paths.get(uploadPath, newFileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            // 返回可访问的URL
            String url = "/uploads/" + newFileName;
            result.put("url", url);
            result.put("error", null);
        } catch (IOException e) {
            result.put("error", "上传失败：" + e.getMessage());
        }
        return result;
    }
}