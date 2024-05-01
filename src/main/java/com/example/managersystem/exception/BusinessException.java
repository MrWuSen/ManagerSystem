package com.example.managersystem.exception;

/**
 * @Author: wusen
 * @Date: 2024/4/30 16:36
 * @Description: 自定义异常
 */
public interface BusinessException {
    Integer getErrorCode();
    String getErrorMessage();
}
