package com.example.managersystem.exception;

import org.springframework.http.HttpStatus;

/**
 * @Author: wusen
 * @Date: 2024/4/30 16:37
 * @Description: 抽象异常类
 */
public abstract class AbstractException extends RuntimeException implements BusinessException {
    public AbstractException(String message) {
        super(message);
    }

    @Override
    public Integer getErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @Override
    public String getErrorMessage() {
        return getMessage();
    }
}
