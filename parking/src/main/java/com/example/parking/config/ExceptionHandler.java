package com.example.parking.config;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        // 构建错误信息
        String errorMessage = "接口请求出错：" + e.getMessage();

        System.out.println(errorMessage);
        // 返回统一格式的错误信息
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("errorMessage", errorMessage);

        return JSON.toJSONString( new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
