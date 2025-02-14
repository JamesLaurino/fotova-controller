package com.fotova.firstapp;

import com.fotova.entity.ProductEntity;
import com.fotova.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.fotova"})
@EntityScan(basePackages = {"com.fotova.entity"})
@EnableJpaRepositories(basePackages = {"com.fotova.repository"})
@ComponentScan(basePackages = {
        "com.fotova.service",
        "com.fotova.config",
        "com.fotova.firstapp.controller",
        "com.fotova.firstapp.security.config.jwt",
        "com.fotova.firstapp.security",
        "com.fotova.firstapp.security.service.user",
        "com.fotova.firstapp.security.service"})
public class FirstAppApplication implements CommandLineRunner {

    @Autowired
    private ProductService productService;


    public static void main(String[] args)
    {
        SpringApplication.run(FirstAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        /* INSERT PRODUCT */
        //productService.getAllProducts().forEach(product -> System.out.println(product.getName()));

    }
}
