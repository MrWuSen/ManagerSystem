package com.example.managersystem.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: wusen
 * @Date: 2024/4/29 17:14
 * @Description: 用户资源文件
 */
@Data
public class UserResource {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户角色
     */
    private String role;
    /**
     * 用户数据位置
     */
    private Integer userLineNo;
    /**
     * 访问权限
     */
    private List<String> endpoint;
}
