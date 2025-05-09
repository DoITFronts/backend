package com.codeit.side.chat.adapter.in.web;

import com.codeit.side.chat.adapter.in.web.request.ChatRoomResponses;
import com.codeit.side.chat.adapter.in.web.response.ChatMessageResponses;
import com.codeit.side.chat.application.port.in.ChatMessageUseCase;
import com.codeit.side.chat.domain.ChatMessages;
import com.codeit.side.chat.domain.ChatRoomInfo;
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

    @PostMapping("/rooms/{id}/join")
    public ResponseEntity<Void> joinChatRoom(@PathVariable Long id) {
        String email = getEmail();
        chatMessageUseCase.joinChatRoom(id, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<ChatRoomResponses> getRooms() {
        String email = getEmail();
        List<ChatRoomInfo> chatRoomsInfo = chatMessageUseCase.findAllChatRooms(email);
        return ResponseEntity.ok(ChatRoomResponses.from(chatRoomsInfo));
    }

    @GetMapping("/rooms/{id}/messages")
    public ResponseEntity<ChatMessageResponses> getMessages(@PathVariable Long id, @RequestParam(required = false) Long offset, @RequestParam(required = false, defaultValue = "20") Integer size) {
        String email = getEmail();
        ChatMessages chatMessages = chatMessageUseCase.findAllMessagesByRoomId(id, email, offset, size);
        return ResponseEntity.ok(ChatMessageResponses.from(chatMessages));
    }

    private String getEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(email)) {
            throw new AuthenticationFailedException("로그인이 필요합니다.");
        }
        return email;
    }
}
