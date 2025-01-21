package com.lsy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义注解(定义存放方法功能信息属性businessName)
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystempLog {
    String businessName();
}
