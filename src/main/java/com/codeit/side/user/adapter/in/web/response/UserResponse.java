package com.codeit.side.user.adapter.in.web.response;

import com.codeit.side.user.domain.User;

public record UserResponse(
        Long id,
        String nickname,
        String email
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getNickname(), user.getEmail());
    }
}
