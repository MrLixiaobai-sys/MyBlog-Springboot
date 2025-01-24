package com.lsy.Config;

import com.lsy.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.swing.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    JWT认证过滤器（redis缓存认证，或者检验token）
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

//    认证失败处理器
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

//    认证授权处理器
    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    //密码加密器(使得当校验密码时,会被加密校验)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/login").anonymous()
                //注销接口需要认证才能访问
                .antMatchers("/logout").authenticated()
                .antMatchers("/user/userInfo").authenticated()
//                .antMatchers("/upload").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();

//        6.默认情况下，Spring Security 会提供一个 /logout 接口，用于注销登录,禁用注销功能,避免与自定义的logout接口冲突
        http.logout().disable();

//        7.配置异常处理类
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);


//        8.把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
//            自定义过滤器添加到 Spring Security 的过滤器链中，插入到 UsernamePasswordAuthenticationFilter之前
//            在前端请求处理时，jwtAuthenticationTokenFilter 会先于 UsernamePasswordAuthenticationFilter 进行请求的处理
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

//        9.允许跨域s
        http.cors();
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
