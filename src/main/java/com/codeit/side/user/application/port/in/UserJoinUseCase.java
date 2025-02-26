package com.codeit.side.user.application.port.in;

import com.codeit.side.user.domain.User;
import com.codeit.side.user.domain.command.UserCommand;

public interface UserJoinUseCase {
    User createUser(UserCommand user);
}
