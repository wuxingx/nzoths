package com.nzoths.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@MapperScan("com.nzoths.persist.mapping")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
