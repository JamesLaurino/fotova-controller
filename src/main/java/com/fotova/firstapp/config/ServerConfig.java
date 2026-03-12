package com.fotova.firstapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("prod")
public class ServerConfig implements WebMvcConfigurer {

    @Value("${app.env.host}")
    private String SERVER_HOST;

    @Value("${app.env.protocol}")
    private String SERVER_PROTOCOL;

    @Value("${file.upload.path}")
    private String UPLOAD_FILE;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                        "classpath:/static/images/",
                        "file:/var/app/images/",
                        "file:/C:/dev/images/",
                        UPLOAD_FILE
                );

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        SERVER_PROTOCOL + "://" + SERVER_HOST,
                        SERVER_PROTOCOL + "://" + SERVER_HOST + ":80",
                        SERVER_PROTOCOL + "://" + SERVER_HOST + ":443",
                        "http://localhost",
                        "http://localhost:80",
                        "http://localhost:443",
                        "http://82.29.172.74",
                        "http://82.29.172.74:80",
                        "http://82.29.172.74:443",
                        "https://82.29.172.74",
                        "https://82.29.172.74:80",
                        "https://82.29.172.74:443",
                        "http://srv827971.hstgr.cloud",
                        "http://srv827971.hstgr.cloud:80",
                        "http://srv827971.hstgr.cloud:443",
                        "https://srv827971.hstgr.cloud",
                        "https://srv827971.hstgr.cloud:80",
                        "https://srv827971.hstgr.cloud:443"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                .allowedHeaders("*");
    }
}
