package com.woniu.config;

import com.woniu.filter.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * spring-security核心配置类
 */
@EnableWebSecurity  //配置安全认证策略
@EnableGlobalMethodSecurity(prePostEnabled = true)    //判断用户对某个控制层的方法是否具有访问权限
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private WorkerDetailsServiceImpl workerDetailsServiceImpl;
    @Autowired
    private UserDetailServiceImp UserDetailServiceImp;

    @Autowired
    private MyAuthenticationSucess sucess;
    @Autowired
    private MyAuthenticationFailu failu;
    @Autowired
    private MyAuthenticationLoginOut logout;
    @Autowired
    private MyAuthenticationEntryPoint noLogin;
    @Autowired
    private MyAuthenticationAcces noAccess;
    @Autowired
    private JwtTokenAuthenticationFilter JwtFilter;

    //密码加密
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(UserDetailServiceImp).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());

        //需要放行的url在这里配置,必须要放行/login,不然会报错
        http.authorizeRequests().antMatchers("/userLogin","/user/add","/payReusult").permitAll().antMatchers("/returnUrl").permitAll()

                .anyRequest().authenticated();
        // 设置登陆页、登录表单form中action的地址，也就是处理认证请求的路径
        http.formLogin().loginProcessingUrl("/userLogin")
            //登录表单form中密码输入框input的name名，不修改的话默认是password
            .usernameParameter("telephone").passwordParameter("password")
            .successHandler(sucess)
            .failureHandler(failu)
            //前后端分离注销
            .and().logout().logoutSuccessHandler(logout)
            //前后分离未登录情况
            .and().exceptionHandling()
            .authenticationEntryPoint(noLogin)
            //前后分离认证成功权限不足情况
            .accessDeniedHandler(noAccess).and()
            //添加一个自定义过滤器到过滤器链中
            .addFilterBefore(JwtFilter, UsernamePasswordAuthenticationFilter.class);
        //关闭session最严格的策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //关闭CSRF跨域
        http.csrf().disable();
    }

    //防止跨域问题
    @Bean
    @Profile("dev")
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");//修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        configuration.addAllowedMethod("*");//修改为添加而不是设置
        configuration.addAllowedHeader("Access-Control-Allow-Origin");//这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
