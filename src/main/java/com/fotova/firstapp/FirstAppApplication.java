package com.fotova.firstapp;

import com.fotova.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@SpringBootApplication(scanBasePackages = {"com.fotova"})
@EntityScan(basePackages = {"com.fotova.entity"})
@EnableJpaRepositories(basePackages = {"com.fotova.repository"})
@EnableRedisRepositories(basePackages = "com.fotova.repository.redis")
@ComponentScan(basePackages = {
        "com.fotova.service",
        "com.fotova.config",
        "com.fotova.repository",
        "com.fotova.firstapp.controller",
        "com.fotova.firstapp.config",
        "com.fotova.firstapp.security.config.jwt",
        "com.fotova.firstapp.security",
        "com.fotova.firstapp.security.service.user",
        "com.fotova.firstapp.security.service"})
public class FirstAppApplication {

    @Autowired
    private ProductService productService;


    public static void main(String[] args)
    {
        SpringApplication.run(FirstAppApplication.class, args);
    }

}
