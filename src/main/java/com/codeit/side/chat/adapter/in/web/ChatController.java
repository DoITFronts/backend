package com.codeit.side.chat.adapter.in.web;

import com.codeit.side.chat.adapter.in.web.request.ChatRequest;
import com.codeit.side.chat.application.port.in.ChatMessageUseCase;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import com.codeit.side.common.adapter.exception.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/join")
public class ChatController {
    private final ChatMessageUseCase chatMessageUseCase;

    @PostMapping
    public void joinChatRoom(@RequestBody ChatRequest chatRequest) {
        String email = getEmail(true);
        ChatRoomCommand chatRoomCommand = chatRequest.toCommand();
        chatMessageUseCase.joinChatRoom(email, chatRoomCommand);
    }

    private String getEmail(boolean required) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (required && "anonymousUser".equals(email)) {
            throw new AuthenticationFailedException("로그인이 필요합니다.");
        }
        if ("anonymousUser".equals(email)) {
            return "";
        }
        return email;
    }
}
