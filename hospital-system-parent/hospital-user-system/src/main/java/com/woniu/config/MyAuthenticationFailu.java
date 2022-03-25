package com.woniu.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woniu.exception.ToKenInvalidException;
import com.woniu.exception.ToKenIsNullException;
import com.woniu.util.ResponseResult;
import com.woniu.util.ResultCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户登录失败多种异常情况
 */
@Component
public class MyAuthenticationFailu implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        ResponseResult result = null;
        if (e instanceof ToKenIsNullException){
            //TOKEN为空
            result = ResponseResult.error(ResultCode.TOKEN_IS_NULL);
        }else if (e instanceof ToKenInvalidException){
            //TOKEN非法
            result = ResponseResult.error(ResultCode.TOKEN_INVALID_EXCEPTION);
        } else if(e instanceof UsernameNotFoundException){
            //账号不存在
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_NOT_EXIST);
        } else if (e instanceof AccountExpiredException) {
            //账号过期
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof BadCredentialsException) {
            //凭证不对   错误
            result = ResponseResult.error(ResultCode.USER_CREDENTIALS_ERROR);
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            result = ResponseResult.error(ResultCode.USER_CREDENTIALS_EXPIRED);
        } else if (e instanceof DisabledException) {
            //账号不可用
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_DISABLE);
        } else if (e instanceof LockedException) {
            //账号锁定
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_LOCKED);
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }else{
            //失败
            result = ResponseResult.error(ResultCode.COMMON_FAIL);
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        //输出JSON
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
