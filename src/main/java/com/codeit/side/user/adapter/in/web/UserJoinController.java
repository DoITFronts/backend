package com.codeit.side.user.adapter.in.web;

import com.codeit.side.user.adapter.in.web.request.UserJoinRequest;
import com.codeit.side.user.adapter.in.web.response.UserJoinResponse;
import com.codeit.side.user.application.port.in.UserUseCase;
import com.codeit.side.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
public class UserJoinController {
    private final UserUseCase userUseCase;

    @PostMapping
    public ResponseEntity<UserJoinResponse> join(@RequestBody @Valid UserJoinRequest request) {
        User user = userUseCase.create(request.toCommand());
        return ResponseEntity.ok(UserJoinResponse.from(user));
    }
}
