package com.codeit.side.user.domain.command;

import java.time.LocalDate;

public class UserCommandFixture {
    public static UserCommand create(String email, String password, String name, String nickname, LocalDate birth) {
        return UserCommand.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .birth(birth)
                .build();
    }

    public static UserCommand create(String email) {
        return UserCommand.builder()
                .email(email)
                .build();
    }
}
