package com.lsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableScheduling
public class FrontendApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }
}
