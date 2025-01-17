package com.lsy.handle;

import com.alibaba.fastjson.JSON;
import com.lsy.domain.ResponseResult;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//授权失败处理器
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseResult result = ResponseResult.errorResult(BlogHttpCodeEnum.NO_OPERATOR_AUTH);

//        将response和转换为json的result
//        向客户端直接返回一个 JSON 格式的字符串作为 HTTP 响应
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
