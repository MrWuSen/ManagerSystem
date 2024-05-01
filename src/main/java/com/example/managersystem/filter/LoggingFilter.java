package com.example.managersystem.filter;

import com.example.managersystem.constant.BaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @Author: wusen
 * @Date: 2024/4/30 20:41
 * @Description: 请求与响应日志
 */
@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    private final Environment environment;

    public LoggingFilter(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Generate UUID and put it into MDC
            MDC.put(BaseConstant.TRANCE_ID, UUID.randomUUID().toString().replace("-", ""));

            Boolean loggingFilterEnable = environment.getProperty(BaseConstant.LOGGING_FILTER_ENABLE, boolean.class, false);
            if(!loggingFilterEnable) {
                filterChain.doFilter(request, response);
                return;
            }
            RequestWrapper requestWrapper = new RequestWrapper(request);
            ResponseWrapper responseWrapper = new ResponseWrapper(response);
            // 记录请求信息
            log.info(generateCurlCommand(requestWrapper));

            filterChain.doFilter(requestWrapper, responseWrapper);
            // 记录响应信息
            log.info("Response : {}", responseWrapper.getCaptureAsString(response));
        } finally {
            // Clear MDC after the request is done
            MDC.remove(BaseConstant.TRANCE_ID);
        }
    }

    private String generateCurlCommand(RequestWrapper request) {
        StringBuilder curlCmd = new StringBuilder("curl -X ").append(request.getMethod());
        curlCmd.append(" '").append(request.getRequestURL().toString());
        if (request.getQueryString() != null) {
            curlCmd.append('?').append(request.getQueryString());
        }
        curlCmd.append("'");

        // Include headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            curlCmd.append(" -H '").append(headerName).append(": ").append(request.getHeader(headerName)).append("'");
        }

        // Handle body for POST and PUT requests
        if ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod())) {
            String body = request.getBodyString();
            curlCmd.append(" --data '").append(body).append("'");
        }

        return curlCmd.toString();
    }
}
