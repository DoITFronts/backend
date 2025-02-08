package com.codeit.side.user.domain;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserFixture {
    public static User create(String email, String password, String name, String nickname, LocalDate birth) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .birth(birth)
                .build();
    }

    public static User create(String email) {
        return User.builder()
                .email(email)
                .build();
    }
}