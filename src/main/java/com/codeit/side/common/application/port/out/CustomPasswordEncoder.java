package com.codeit.side.common.application.port.out;

public interface CustomPasswordEncoder {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
