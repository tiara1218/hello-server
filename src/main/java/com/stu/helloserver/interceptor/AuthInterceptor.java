package com.stu.helloserver.interceptor;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.common.ResultCode;
import com.stu.helloserver.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的 Token
        String token = request.getHeader(AUTHORIZATION_HEADER);

        // 验证 Token
        if (token == null || token.isEmpty() || !JwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            Result<Object> errorResult = Result.error(ResultCode.TOKEN_INVALID);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(errorResult);
            response.getWriter().write(json);

            return false;
        }

        // 将用户名存入请求属性中，供后续使用
        String username = JwtUtil.extractUsername(token);
        request.setAttribute("username", username);

        return true;
    }
}