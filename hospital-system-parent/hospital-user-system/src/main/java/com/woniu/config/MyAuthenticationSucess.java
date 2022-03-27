package com.woniu.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.util.JwtTokenUitl;
import com.woniu.util.ResponseResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户登录成功
 */
@Component
public class MyAuthenticationSucess implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            String telephone = request.getParameter("telephone");
            String tokenStr = JwtTokenUitl.createSign(telephone);

            ResponseResult<String> result = new ResponseResult(tokenStr,"登录成功",200);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer =  response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
