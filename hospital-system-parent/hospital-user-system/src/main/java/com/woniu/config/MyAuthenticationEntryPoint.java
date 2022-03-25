package com.woniu.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.util.ResponseResult;
import com.woniu.util.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 前后端分离用户未登录
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        ResponseResult result = ResponseResult.error(ResultCode.USER_NOT_LOGIN);

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer =  response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
