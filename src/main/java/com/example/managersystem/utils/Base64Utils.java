package com.example.managersystem.utils;

import com.alibaba.fastjson.JSON;
import com.example.managersystem.dto.UserInfo;
import com.example.managersystem.enums.UserType;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author: wusen
 * @Date: 2024/4/29 14:34
 * @Description: base64 工具类
 */
public class Base64Utils {

    public static String encodeToString(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeToString(String encodeInput) {
        byte[] decodeInput = Base64.getDecoder().decode(encodeInput);
        return new String(decodeInput);
    }

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("123456");
        userInfo.setAccountName("测试");
        userInfo.setRole(UserType.ADMIN.getRole());
        System.out.println(encodeToString(JSON.toJSONString(userInfo)));
        // eyJhY2NvdW50TmFtZSI6Iua1i+ivlSIsInJvbGUiOiJhZG1pbiIsInVzZXJJZCI6IjEyMzQ1NiJ9
        userInfo.setRole(UserType.USER.getRole());
        System.out.println(encodeToString(JSON.toJSONString(userInfo)));
        // eyJhY2NvdW50TmFtZSI6Iua1i+ivlSIsInJvbGUiOiJ1c2VyIiwidXNlcklkIjoiMTIzNDU2In0=
    }
}
