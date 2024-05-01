package com.example.managersystem.exception;

import com.example.managersystem.constant.BaseConstant;
import com.example.managersystem.dto.EmptyBody;
import com.example.managersystem.dto.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: wusen
 * @Date: 2024/4/30 16:51
 * @Description: 全局异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseModel<?> resolveException(Exception ex) {
        log.error("系统运行出现异常，异常：", ex);
        if (ex instanceof CustomBusinessException) {
            CustomBusinessException businessException = (CustomBusinessException) ex;
            return getErrorMessage(businessException.getErrorCode(), businessException.getErrorMessage());
        } else {
            return getDefaultMessage(ex);
        }
    }

    private ResponseModel<?> getErrorMessage(int code, String message) {
        return ResponseModel.error(code, message, EmptyBody.INSTANCE);
    }

    private ResponseModel<?> getDefaultMessage(Exception ex) {
        return ResponseModel.error(BaseConstant.UNKNOWN_ERROR, ex.getMessage(), EmptyBody.INSTANCE);
    }
}
