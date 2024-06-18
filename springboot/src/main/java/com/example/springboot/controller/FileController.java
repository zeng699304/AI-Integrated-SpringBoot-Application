package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;

/**
 * 功能：
 * 作者：小曾曾
 * 日期：2024/5/17 19:56
 */
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    private String ip;

    @Value("${server.port:8080}")
    private String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + "/files";

    // 假设Flask服务的URL为 http://localhost:5000/predict
    private final String FLASK_SERVICE_URL = "http://localhost:5000/predict";

    // 添加一个RestTemplate用于调用Flask服务
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //文件的原始名称（文件名+类型）
        //aaa.png
        String mainName = FileUtil.mainName(originalFilename);//文件的名称,去除后缀  aaa
        String extName = FileUtil.extName(originalFilename);//文件的后缀名  png
        if(!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH); //如果当前文件的父目录不存在，就创建父目录
        }
        if(FileUtil.exist(ROOT_PATH + "/" + originalFilename)){
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + "/" + originalFilename);
        file.transferTo(saveFile);
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);
    }

    @PostMapping("/detect")
    public Result detect(@RequestBody List<String> fileUrls) throws IOException {
        List<String> processedUrls = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        for (String fileUrl : fileUrls) {
            // 从URL中提取文件名
            String filename = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
            // 创建FileSystemResource
            FileSystemResource resource = new FileSystemResource(ROOT_PATH + "/" + filename);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", resource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(FLASK_SERVICE_URL, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                // 因为 "filenames" 是一个数组，我们需要检查它是否存在并且是否是一个数组
                JsonNode filenamesNode = rootNode.path("urls");
                if (filenamesNode.isArray()) {
                    // 遍历数组
                    for (JsonNode filenameNode : filenamesNode) {
                        String processedFilename = filenameNode.asText(); // 获取单个文件名
                        String processedUrl = "http://" + ip + ":" + port + "/file/download/" + processedFilename;
                        processedUrls.add(processedUrl); // 将处理后的URL添加到列表中
                    }
                }
            } else {
                return Result.error("Failed to get prediction from YOLOv5 Model");
            }
        }
        return Result.success(processedUrls);
    }

//    @GetMapping("/download/{filename}")
//    @AuthAccess
//    public void download(@PathVariable String filename, HttpServletResponse response) throws IOException {
////        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(filename, "UTF-8"));  // 预览
//        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8")); // 附件下载
//        System.out.println("得到的："+filename);
//        String filePath = ROOT_PATH + "/" + filename;
//        System.out.println("最终的："+filePath);
//        if(!FileUtil.exist(filePath)){
//            return;
//        }
//        byte[] bytes = FileUtil.readBytes(filePath);//读取文件的字节流
//        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(bytes); //数组是一个字节数组，可以是文件的字节流数组
//        outputStream.flush();
//        outputStream.close();
//    }

    @GetMapping("/download/**")
    @AuthAccess
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullPath = extractPathFromPattern(request);

        // 替换掉前缀 "/download/" 后，剩下的就是文件路径
        String filePath = fullPath.replaceFirst("/download/", "");
        String fileLocation = ROOT_PATH + "/" + filePath;

        System.out.println(fileLocation);
        if(!FileUtil.exist(fileLocation)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filePath, "UTF-8"));
        byte[] bytes = FileUtil.readBytes(fileLocation);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes); //数组是一个字节数组，可以是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }

    private String extractPathFromPattern(final HttpServletRequest request){
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }
}