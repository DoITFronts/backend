package com.codeit.side.user.application.service;

import com.codeit.side.common.adapter.exception.EmailAlreadyExistsException;
import com.codeit.side.common.adapter.exception.ErrorCode;
import com.codeit.side.common.application.port.out.CustomPasswordEncoder;
import com.codeit.side.user.application.port.in.UserJoinUseCase;
import com.codeit.side.user.application.port.out.UserCommandRepository;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import com.codeit.side.user.domain.User;
import com.codeit.side.user.domain.command.UserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserJoinService implements UserJoinUseCase {
    private final UserQueryRepository userQueryRepository;
    private final UserCommandRepository userCommandRepository;
    private final CustomPasswordEncoder encoder;

    @Override
    @Transactional
    public User createUser(UserCommand userCommand) {
        if (userQueryRepository.existsByEmail(userCommand.email())) {
            throw new EmailAlreadyExistsException(ErrorCode.INVALID_INPUT_VALUE, userCommand.email() + " 이미 가입된 이메일입니다.");
        }
        User user = userCommand.toDomain(encoder);

        return userCommandRepository.saveUser(user);
    }
}
