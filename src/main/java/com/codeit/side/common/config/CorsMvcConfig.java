package com.codeit.side.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    private static final String[] ALLOWED_ORIGINS = {"http://localhost:3000", "http://127.0.0.1:5500", "https://doitz.netlify.app/", "https://calit.netlify.app/"};
    private static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(ALLOWED_METHODS);
    }
}
