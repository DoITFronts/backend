package com.codeit.side.user.adapter.in.web.response;

import com.codeit.side.user.domain.User;

public record UserResponse(
        Long id,
        String nickname,
        String email,
        String description,
        String imageUrl
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getDescription(),
                user.isHasImage() ? "https://codeit-doit.s3.ap-northeast-2.amazonaws.com/user/%s/image.jpg".formatted(user.getId()) : ""
        );
    }
}
