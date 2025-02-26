package com.codeit.side.user.domain.command;

import com.codeit.side.common.application.port.out.CustomPasswordEncoder;
import com.codeit.side.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;

@Builder(access = AccessLevel.PACKAGE)
public record UserCommand(
        String email,
        String password,
        String name,
        String nickname,
        LocalDate birth
) {
    public static UserCommand of(String email, String password, String name, String nickname, LocalDate birth) {
        return new UserCommand(email, password, name, nickname, birth);
    }

    public User toDomain(CustomPasswordEncoder encoder) {
        return User.of(email, encoder.encode(password), name, nickname, birth);
    }
}
