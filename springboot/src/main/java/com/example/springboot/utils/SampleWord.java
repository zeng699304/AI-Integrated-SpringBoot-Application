package com.example.springboot.utils;

/**
 * 功能：
 * 作者：小曾曾
 * 日期：2024/6/17 13:07
 */

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;
public class SampleWord {


    public static void main(String[] args) {
        //设置APPID/AK/SK
        String APP_ID = "83580023";
        String API_KEY = "0urM8I7uzI7BzZ9lvjJuyuC5";
        String SECRET_KEY = "1NZgFzChNR8YHYNdvYZwdZ49HBO8uj5P";

        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 调用接口
        String path = "G:\\1.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }
}
