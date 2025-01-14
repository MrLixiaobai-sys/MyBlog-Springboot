package com.lsy.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
//                        .allowedOriginPatterns("*")
                        .allowedOrigins("http://localhost:8080") // 允许的来源:前端端口
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的方法
                        .allowedHeaders("*") // 允许的请求头
                        .allowCredentials(true) // 是否允许携带 Cookie
                        .maxAge(3600);
            }

}
