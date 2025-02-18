package com.codeit.side.common.adapter.out.security;

import com.codeit.side.common.application.port.out.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * SpringSecurity 의존성을 가진 password encoder
 */
@Primary
@Component
@RequiredArgsConstructor
public class SecurityPasswordEncoder implements CustomPasswordEncoder {
    private final PasswordEncoder bcryptPasswordEncoder;

    @Override
    public String encode(CharSequence rawPassword) {
        return bcryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
