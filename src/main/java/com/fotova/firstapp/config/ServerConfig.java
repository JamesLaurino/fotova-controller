package com.fotova.firstapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServerConfig implements WebMvcConfigurer {

    @Value("${SERVER_HOST}")
    private String SERVER_HOST;

    @Value("${SERVER_PROTOCOL}")
    private String SERVER_PROTOCOL;

    @Value("${UPLOAD_FILE}")
    private String UPLOAD_FILE;

    @Value("${FRONT_PORT}")
    private String FRONT_PORT;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                        "classpath:/static/images/",
                        "file:/" + UPLOAD_FILE /* répertoire externe */
                );

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(SERVER_PROTOCOL + "://" + SERVER_HOST + ":" + FRONT_PORT)
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                .allowedHeaders("*");
    }
}
