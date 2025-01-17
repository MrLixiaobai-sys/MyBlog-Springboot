package com.lsy.handle;

import com.alibaba.fastjson.JSON;
import com.lsy.domain.ResponseResult;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//认证授权失败异常处理 默认调用AuthenticationEntryPoint
//将异常返回类型封装为json格式返回给前端
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = null;
        if(e instanceof BadCredentialsException){
//                                                                           1.用户或密码错误
//              将枚举的code与传入异常的message封装到响应体
            result = ResponseResult.errorResult(BlogHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else if (e instanceof InsufficientAuthenticationException) {       //2.权限不足
            result =ResponseResult.errorResult(BlogHttpCodeEnum.NEED_LOGIN);
        }else {                                                             //3.其他异常请况
            result =ResponseResult.errorResult(BlogHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }

//        将响应，以及异常结果返回给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));

    }
}
