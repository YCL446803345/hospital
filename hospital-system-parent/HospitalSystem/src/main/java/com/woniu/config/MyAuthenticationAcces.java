package com.woniu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.util.ResponseResult;
import com.woniu.util.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 前后端分离针对认证成功后权限不足情况
 */
@Component
public class MyAuthenticationAcces implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        ResponseResult result = ResponseResult.error(ResultCode.NO_PERMISSION);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer =  response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
