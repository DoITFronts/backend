package com.codeit.side.user.adapter.in.web.response;

public record UserJoinResponse(String email, String name, String nickname) {
    public static UserJoinResponse from(com.codeit.side.user.domain.User user) {
        return new UserJoinResponse(user.getEmail(), user.getName(), user.getNickname());
    }
}
