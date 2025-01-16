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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.swing.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //密码加密器(使得当校验密码时,会被加密校验)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

//        关闭csrf
                .csrf().disable()
//        不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//        对于登录接口 允许匿名访问 允许未登录的匿名用户访问 /login 接口。
                .antMatchers("/login").anonymous()

                    //jwt过滤器测试用，如果测试没有问题吧这里删除了
//                .antMatchers("/Link/getAllLink").authenticated()

//        除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();
//        默认情况下，Spring Security 会提供一个 /logout 接口，用于注销登录,禁用注销功能
        http.logout().disable();

//        把jwtAuthenticationTokenFilter添加到SpringSecurity的过滤器链中
//        自定义过滤器添加到 Spring Security 的过滤器链中，插入到 UsernamePasswordAuthenticationFilter之前
//        在前端请求处理时，jwtAuthenticationTokenFilter 会先于 UsernamePasswordAuthenticationFilter 进行请求的处理
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        允许跨域s
        http.cors();
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
