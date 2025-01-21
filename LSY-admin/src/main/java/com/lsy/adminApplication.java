package com.lsy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lsy.mapper")  //会扫描所有模块下com.lsy.mapper的mapper接口(无需添加@Mapper注解)
public class adminApplication {
    public static void main(String[] args) {
        SpringApplication.run(adminApplication.class, args);
    }
}
