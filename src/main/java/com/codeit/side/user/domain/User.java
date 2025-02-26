package com.codeit.side.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final String email;
    private final String password;
    private final String name;
    private final String nickname;
    private final LocalDate birth;

    public static User of(String email, String password, String name, String nickname, LocalDate birth) {
        return new User(email, password, name, nickname, birth);
    }
}
