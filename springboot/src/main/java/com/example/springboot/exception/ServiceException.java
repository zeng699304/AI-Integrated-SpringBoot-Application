package com.example.springboot.exception;

import lombok.Data;
import lombok.Getter;

/**
 * 功能：
 * 作者：小曾曾
 * 日期：2024/5/10 0:58
 */
@Data
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String msg) {
        super(msg);
        this.code="500";
    }
    public ServiceException(String code,String msg) {
        super(msg);
        this.code = code;
    }
}