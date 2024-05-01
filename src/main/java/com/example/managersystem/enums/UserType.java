package com.example.managersystem.enums;

/**
 * @Author: wusen
 * @Date: 2024/4/29 15:25
 * @Description: 用户类型
 */
public enum UserType {

    ADMIN("admin"),
    USER("user");

    private String role;

    UserType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
