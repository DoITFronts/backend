package com.codeit.side.user.adapter.in.web;

import com.codeit.side.user.adapter.in.web.request.UserJoinRequest;
import com.codeit.side.user.adapter.in.web.response.UserJoinResponse;
import com.codeit.side.user.application.port.in.UserJoinUseCase;
import com.codeit.side.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
public class UserJoinController {
    private final UserJoinUseCase userJoinUseCase;

    @PostMapping
    public ResponseEntity<UserJoinResponse> joinUser(@Valid UserJoinRequest request) {
        User user = userJoinUseCase.createUser(request.toCommand());
        return ResponseEntity.ok(UserJoinResponse.from(user));
    }

    @GetMapping
    public String test() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
