package com.codeit.side.common.config;

import com.codeit.side.common.adapter.in.filter.JwtAuthenticationFilter;
import com.codeit.side.common.adapter.in.filter.LoginFilter;
import com.codeit.side.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final List<String> ALLOWED_ORIGINS = List.of(
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
            "https://www.thunderting-site.com"
    );
    private static final List<String> ALLOWED_METHODS = List.of("GET", "POST", "PUT", "DELETE");
    private static final String[] WHITE_LIST = {
            "/",
            "/api/v1/login",
            "/api/v1/join",
            "/api/v1/**",
            "/api/v1/ws/**",
            "/api/v1/ws",
    };

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.formLogin(AbstractHttpConfigurer::disable);

        http.httpBasic(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITE_LIST)
                .permitAll()
                .anyRequest().authenticated());

        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        http.addFilterAfter(loginFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.cors(this::getHttpSecurityCorsConfigurer);

        return http.build();
    }

    private void getHttpSecurityCorsConfigurer(CorsConfigurer<HttpSecurity> corsCustomizer) {
        corsCustomizer.configurationSource(request -> getCorsConfiguration());
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(ALLOWED_ORIGINS);
        corsConfiguration.setAllowedMethods(ALLOWED_METHODS);
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.setMaxAge(3600L);

        return corsConfiguration;
    }
}
