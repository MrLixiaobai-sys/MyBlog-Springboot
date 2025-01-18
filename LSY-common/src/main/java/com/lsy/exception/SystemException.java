package com.lsy.exception;

import com.lsy.enums.BlogHttpCodeEnum;

public class SystemException extends RuntimeException{
    //错误响应码
    private Integer code;

    //错误信息
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public SystemException(BlogHttpCodeEnum blogHttpCodeEnum){
        super(blogHttpCodeEnum.getMsg());
        this.code = blogHttpCodeEnum.getCode();
        this.message = blogHttpCodeEnum.getMsg();
    }

    public SystemException(Integer code,String message){
        super(message);
        this.code = code;
        this.message = message;
    }

}
