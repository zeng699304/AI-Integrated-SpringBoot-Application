package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.utils.AiUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 功能：
 * 作者：小曾曾
 * 日期：2024/6/17 14:56
 */
@RestController
public class AiController {
    @GetMapping(value = "/nlp",produces = "application/json;charset=utf-8")
    public Result nlp(@RequestParam("text") String text){
        String res = AiUtils.nlp(text);
        return Result.success(res);
    }

    @PostMapping(value = "/word", produces = "application/json;charset=utf-8")
    public Result nlp(@RequestParam("file") MultipartFile file) throws IOException {
        String res = AiUtils.word(file);
        return Result.success(res);
    }
}