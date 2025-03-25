package com.codeit.side.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    private static final String[] ALLOWED_ORIGINS = {
            "http://localhost:3000",
            "http://127.0.0.1:5500",
            "https://thunderting.site",
            "https://doitz.netlify.app",
            "https://thunderting-doitz.vercel.app",
            "https://thunderting-one.vercel.app",
            "https://thunderting-hong.netlify.app",
            "https://thunderting-one.vercel.app",
            "https://thundertingdoit.netlify.app",
            "https://thunderting.netlify.app",
            "https://www.thunderting-site.com",
    };
    private static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(ALLOWED_METHODS)
                .allowCredentials(true);
    }
}
