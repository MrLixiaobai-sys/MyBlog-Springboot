package com.lsy.handle.exception;

import com.lsy.domain.ResponseResult;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class globalException {


    //RunTime异常(没有传入用户名属性或密码等)
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){

        log.error("出现异常!{}",e);
        return ResponseResult.errorResult(e.getCode(), e.getMessage());
    }


    //其他异常
    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler(Exception e){

        log.error("出现异常!{}",e);
        return ResponseResult.errorResult( BlogHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }



}
