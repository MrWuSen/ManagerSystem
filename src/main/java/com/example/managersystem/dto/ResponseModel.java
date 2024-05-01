package com.example.managersystem.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @Author: wusen
 * @Date: 2024/4/30 16:05
 * @Description: 通用返回体
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseModel<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResponseModel<T> ok(final T data) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(data);
        return response;
    }

    public static ResponseModel<EmptyBody> empty() {
        return ok(EmptyBody.INSTANCE);
    }

    public static <T> ResponseModel<T> error(int code, String message, T data) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
