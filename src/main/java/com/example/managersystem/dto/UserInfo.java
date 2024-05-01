package com.example.managersystem.dto;

import lombok.Data;

/**
 * @Author: wusen
 * @Date: 2024/4/29 15:02
 * @Description: 用户信息
 */
@Data
public class UserInfo {
    private String userId;
    private String accountName;
    private String role;
}
