package com.codeit.side.user.adapter.in.web;

import com.codeit.side.common.adapter.exception.AuthenticationFailedException;
import com.codeit.side.lightening.adapter.in.web.response.LighteningResponses;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import com.codeit.side.lightening.domain.*;
import com.codeit.side.user.adapter.in.web.response.UserResponse;
import com.codeit.side.user.application.port.in.UserUseCase;
import com.codeit.side.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/my-page")
public class UserController {
    private final UserUseCase userUseCase;
    private final LighteningUseCase lighteningUseCase;
    
    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser() {
        String email = getEmail();
        User user = userUseCase.getUser(email);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping("/lightening/created")
    public ResponseEntity<LighteningResponses> getCreatedLightenings(@RequestParam String category, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer page) {
        String email = getEmail();
        LighteningCondition lighteningCondition = LighteningCondition.of(
                category,
                ConditionType.MY_CREATED,
                LighteningPaging.of(page, size),
                LighteningOrder.TARGET_DATE_DESC.name(),
                email
        );
        List<LighteningInfo> lighteningInfos = lighteningUseCase.findAllBy(email, lighteningCondition);
        return ResponseEntity.ok(LighteningResponses.from(email, lighteningInfos));
    }

    private String getEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(email)) {
            throw new AuthenticationFailedException("로그인이 필요합니다.");
        }
        return email;
    }
}
