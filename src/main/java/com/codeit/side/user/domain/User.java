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
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String nickname;
    private final LocalDate birth;
    private final String description;
    private final boolean hasImage;

    public static User of(String email, String password, String name, String nickname, LocalDate birth) {
        return of(null, email, password, name, nickname, birth, null, false);
    }

    public static User of(Long id, String email, String password, String name, String nickname, LocalDate birth, String description, boolean hasImage) {
        return new User(id, email, password, name, nickname, birth, description, hasImage);
    }
}
