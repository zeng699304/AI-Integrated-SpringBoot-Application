package com.example.springboot.utils;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

/**
 * 功能：
 * 作者：小曾曾
 * 日期：2024/6/17 13:28
 */

public class SampleNLP {

    public static void main(String[] args) {
        //设置APPID/AK/SK
        String APP_ID = "83587380";
        String API_KEY = "SejKA5DC3EQSZjaiF3nBvrVJ";
        String SECRET_KEY = "zLy6BFZN3JizljtMgtT0lnWmH8KJyuq8";

        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 调用接口
        String text = "百度是一家高科技公司";
        JSONObject res = client.ecnet(text, null);
        System.out.println(res.toString(2));

    }
}