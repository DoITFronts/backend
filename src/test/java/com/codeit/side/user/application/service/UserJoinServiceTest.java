package com.codeit.side.user.application.service;

import com.codeit.side.common.adapter.exception.EmailAlreadyExistsException;
import com.codeit.side.mock.FakeCustomPasswordEncoder;
import com.codeit.side.mock.FakeUserCommandRepository;
import com.codeit.side.mock.FakeUserQueryRepository;
import com.codeit.side.user.domain.User;
import com.codeit.side.user.domain.command.UserCommand;
import com.codeit.side.user.domain.command.UserCommandFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserJoinServiceTest {
    @Test
    @DisplayName("유저 생성 서비스는 유저를 생성해서 응답한다.")
    void createUserReturnsUser() {
        //given
        UserService userJoinService = new UserService(new FakeUserQueryRepository(), new FakeUserCommandRepository(), new FakeCustomPasswordEncoder());
        UserCommand user = UserCommandFixture.create("bht9011@gmail.com", "password~", "박병훈", "byung123", LocalDate.now());
        //when
        //then
        User actual = userJoinService.create(user);
        assertThat(actual.getEmail()).isEqualTo(user.email());
        assertThat(actual.getName()).isEqualTo(user.name());
        assertThat(actual.getNickname()).isEqualTo(user.nickname());
        assertThat(actual.getBirth()).isEqualTo(user.birth());
    }

    @ParameterizedTest
    @DisplayName("중복된 이메일을 등록하면 DuplicateEmailException을 던진다.")
    @ValueSource(strings = {"bht9011@gmail.com", "asdf@google.com"})
    void createUserThrowsDuplicateEmailException(String email) {
        //given
        List<User> users = new ArrayList<>();
        FakeUserQueryRepository userQueryRepository = new FakeUserQueryRepository(users);
        FakeUserCommandRepository userCommandRepository = new FakeUserCommandRepository(users);
        UserService userJoinService = new UserService(userQueryRepository, userCommandRepository ,new FakeCustomPasswordEncoder());
        UserCommand user = UserCommandFixture.create(email, "password~", "박병훈", "byung123", LocalDate.now());
        userJoinService.create(user);
        //when
        //then
        assertThatThrownBy(() -> userJoinService.create(UserCommandFixture.create(email)))
                .isInstanceOf(EmailAlreadyExistsException.class);
    }

    @ParameterizedTest
    @DisplayName("유저를 데이터베이스에 저장한다.")
    @ValueSource(strings = {"park@gmail.com", "asdf@google.com"})
    void createUserToDatabase(String email) {
        //given
        List<User> users = new ArrayList<>();
        FakeUserQueryRepository userQueryRepository = new FakeUserQueryRepository(users);
        FakeUserCommandRepository userCommandRepository = new FakeUserCommandRepository(users);
        FakeCustomPasswordEncoder encoder = new FakeCustomPasswordEncoder();
        UserService userJoinService = new UserService(userQueryRepository, userCommandRepository, encoder);
        UserCommand user = UserCommandFixture.create(email, "password~", "박병훈", "byung123", LocalDate.of(1999, 9, 11));
        //when
        User actual = userJoinService.create(user);
        //then
        assertThat(userQueryRepository.existsByEmail(user.email())).isTrue();
        assertThat(actual.getEmail()).isEqualTo(user.email());
        assertThat(actual.getPassword()).isEqualTo(encoder.encode(user.password()));
        assertThat(actual.getName()).isEqualTo(user.name());
        assertThat(actual.getNickname()).isEqualTo(user.nickname());
        assertThat(actual.getBirth()).isEqualTo(user.birth());
    }
}
