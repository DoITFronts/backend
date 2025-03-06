package com.codeit.side.chat.adapter.in.web;

import com.codeit.side.chat.adapter.in.web.request.ChatRequest;
import com.codeit.side.chat.adapter.in.web.request.ChatRoomResponses;
import com.codeit.side.chat.application.port.in.ChatMessageUseCase;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import com.codeit.side.common.adapter.exception.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatMessageUseCase chatMessageUseCase;

    @PostMapping("/join")
    public void joinChatRoom(@RequestBody ChatRequest chatRequest) {
        String email = getEmail();
        ChatRoomCommand chatRoomCommand = chatRequest.toCommand();
        chatMessageUseCase.joinChatRoom(email, chatRoomCommand);
    }

    @GetMapping
    public ResponseEntity<ChatRoomResponses> getRooms() {
        String email = getEmail();
        List<ChatRoom> chatRooms = chatMessageUseCase.findAllChatRooms(email);
        return ResponseEntity.ok(ChatRoomResponses.from(chatRooms));
    }


    private String getEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(email)) {
            throw new AuthenticationFailedException("로그인이 필요합니다.");
        }
        return email;
    }
}
