package com.punsara.priceenginecalculator.priceenginecalculatorbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Punsara Prathibha
 * Sets us the system configurations
 */
@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    /**
     * @param registry : org.springframework.web.servlet.config.annotation.CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods(String.valueOf(HttpMethod.GET));
    }
}
