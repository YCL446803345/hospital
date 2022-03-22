package com.woniu.filter;

import com.woniu.config.MyAuthenticationFailu;
import com.woniu.exception.ToKenInvalidException;
import com.woniu.exception.ToKenIsNullException;
import com.woniu.mapper.WorkerMapper;
import com.woniu.util.JwtTokenUitl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * token认证过滤器
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private MyAuthenticationFailu failu;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain Chain) throws ServletException, IOException {
        //如果是登录请求，放行
        String uri = request.getRequestURI().toString();
        if (uri.equals("/login")) {
            Chain.doFilter(request, response);
            return;
        }
        //拿到tokenStr
        String tokenStr = request.getHeader("tokenStr");
        if (StringUtils.isEmpty(tokenStr)) {
            //判断是否是认证请求
            failu.onAuthenticationFailure(request, response, new ToKenIsNullException("token为空"));
            return;
        }
        //如果是预检  就return
        if(request.getMethod().equals(HttpMethod.OPTIONS.toString())){
            return ;
        }

        try {
            //检查token是否有效
            if (JwtTokenUitl.checkSign(tokenStr)) {
                //获取用户名
                String userName = JwtTokenUitl.getUserName(tokenStr);
                //通过用户名查询该用户的权限集合
                List<String> preCodes = workerMapper.selectPercodeByPerm(userName);
                List<GrantedAuthority> authorities = new ArrayList<>();
                preCodes.forEach(preCode -> {
                    authorities.add(new SimpleGrantedAuthority(preCode));
                });
                //将消息封装到认证对象中
                Authentication authen =
                        new UsernamePasswordAuthenticationToken(userName, "", authorities);
                //存入security上下文中
                SecurityContextHolder.getContext().setAuthentication(authen);
                //放行
                Chain.doFilter(request, response);
            }
        } catch (Exception e) {   //无效情况
            e.printStackTrace();
            failu.onAuthenticationFailure(request, response, new ToKenInvalidException("token无效"));
        }
    }
}
