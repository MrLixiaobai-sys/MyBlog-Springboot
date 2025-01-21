package com.lsy.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lsy.Controller"))
                .build();
    }
    private ApiInfo apiInfo() {
        Contact contact = new Contact("没有团队的团队", "http://www.lsy888888.com", "lsy888888.com");
        return new ApiInfoBuilder()
                .title("LSY-Blog-Api-Documentation")
                .description("LSY博客的在线接口文档")
                .contact(contact) // 联系方式
                .version("1.1.0") // 版本
                .build();
    }
}
