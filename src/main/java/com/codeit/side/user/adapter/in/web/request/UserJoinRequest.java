package com.codeit.side.user.adapter.in.web.request;

import com.codeit.side.user.domain.command.UserCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record UserJoinRequest(
        @Email(message = "이메일 형식이 잘못되었습니다.") String email,
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문,숫자,특수문자 조합입니다.") String password,
        @NotNull(message = "이름은 필수입니다.") @Length(max = 10, message = "이름은 최대 10자입니다.") String name,
        @Length(max = 10, message = "닉네임은 최대 10자입니다.") String nickname,
        @NotNull(message = "생년월일은 필수입니다.") LocalDate birth
) {
    public UserCommand toCommand() {
        return UserCommand.of(email, password, name, nickname, birth);
    }
}
