package com.example.managersystem.exception;

/**
 * @Author: wusen
 * @Date: 2024/4/30 16:43
 * @Description: 自定义业务异常
 */
public class CustomBusinessException extends AbstractException {

    private Integer errorCode;
    private String errorMessage;

    public CustomBusinessException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
