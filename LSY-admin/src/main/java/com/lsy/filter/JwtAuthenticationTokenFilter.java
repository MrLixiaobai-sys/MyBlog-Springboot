package com.lsy.filter;

import com.alibaba.fastjson.JSON;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.LoginUser;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.utils.JwtUtil;
import com.lsy.utils.RedisCache;
import com.lsy.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/*
    对于登录功能，登录后会生成userId和对应的token，并且保存到redis缓存中，返回给前端
    前端会保存该token，并且调用需要授权的接口时会携带该token给后端
    Jwt过滤器主要实现功能：
    对于前端响应到后端的信息，携带token
    1.过滤器会直接通过校验token是否合法
        是否含有token：含有则表示需要授权，没有表示不需要授权直接放行即可
    2.携带token是否过时效（过期），或者被非法篡改，将提示重新登录
    3.如果合法：从redis中获取对应用户信息
        1.如果获取到为null则表示redis中缓存登录失效（需要重新登录）
        2.如果获取到则将用户信息存入SecurityContextHolder
        将封装了用户信息的 authenticationToken 对象设置到 SecurityContext 中。
        后续的请求中，Spring Security 会自动使用这个 authenticationToken 来识别当前用户

 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private RedisCache redisCache;

    //前端响应后端时会经过OncePerRequestFilter过滤器
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //1.获取前端请求头响应的token
        String token = httpServletRequest.getHeader("token");

//        2.判断是否含有token（有的接口不需要携带token)
        if(Objects.isNull(token)){
            //直接放行并返回
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

//        3.判断token是否合法（过期或非法）

        Claims claims =null;
        try {
            //解析token
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
//            返回异常：需要重新登录(使用响应结果类，枚举的属性） 响应为json格式结果
            ResponseResult responseResult = ResponseResult.errorResult(BlogHttpCodeEnum.NEED_LOGIN);

//            将responseResult转换为json格式并且返回给客户端
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(responseResult));
            return;
        }
//        4.合法时：从claims中获取userId
//        JWT的三部分payload里面就有claims，且这个claims里面的sub即subject就是userId
        String userId = claims.getSubject();

//        5.根据获取的userId从redis中查询对应用户信息(通过key查询value，这里的key一定要是跟登录存储的key一样）
        LoginUser loginUser = redisCache.getCacheObject("adminlogin:"+userId);
//        如果缓存中不存在
        if(Objects.isNull(loginUser)){
//            返回异常：需要重新登录(使用响应结果类，枚举的属性） 响应为json格式结果
            ResponseResult responseResult = ResponseResult.errorResult(BlogHttpCodeEnum.NEED_LOGIN);

//            将responseResult转换为json格式并且返回给客户端
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(responseResult));
//            缓存不存在，直接返回
            return;
        }

//        6.如果缓存中存在，存入SecurityContextHolder后续Spring Security 会自动使用这个 authenticationToken 来识别当前用户
//        principal：用户的身份信息，通常是用户对象或用户名。
//        credentials：用户的凭证，通常是密码。
//        authorities：用户的权限集合，用于标识用户的角色和权限
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest,httpServletResponse);


    }
}
