package com.example.managersystem.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.managersystem.constant.BaseConstant;
import com.example.managersystem.dto.UserInfo;
import com.example.managersystem.enums.UserType;
import com.example.managersystem.exception.CustomBusinessException;
import com.example.managersystem.utils.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wusen
 * @Date: 2024/4/29 14:43
 * @Description: 登录认证拦截器
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static ThreadLocal<HttpHeaders> requestHeader = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String inputUserInfo = request.getHeader(BaseConstant.USER_INFO);
        if(StringUtils.isEmpty(inputUserInfo)) {
            throw new CustomBusinessException(500, "缺失必要header参数：userInfo");
        }
        UserInfo userInfo = JSONObject.parseObject(Base64Utils.decodeToString(inputUserInfo), UserInfo.class);
        log.info("获取用户信息，{}", JSON.toJSONString(userInfo));
        requestHeader.set(new HttpHeaders() {{
            setContentType(MediaType.APPLICATION_JSON);
            add(BaseConstant.USER_ID, userInfo.getUserId());
            add(BaseConstant.ACCOUNT_NAME, userInfo.getAccountName());
            add(BaseConstant.ROLE, userInfo.getRole());
        }});
        if (verifyAdminUrl(request.getRequestURI())) {
            if (userInfo.getRole().equals(UserType.ADMIN.getRole())) {
                return true;
            }
            log.error("用户: {} 暂未有访问权限", userInfo.getUserId());
            throw new CustomBusinessException(500, "the current user has no access to this system");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        requestHeader.remove();
    }

    private boolean verifyAdminUrl(String url) {
        for (String knownUrl : BaseConstant.ADMIN_INTERCEPTOR_URL) {
            if (url.contains(knownUrl)) {
                return true;
            }
        }
        return false;
    }
}
