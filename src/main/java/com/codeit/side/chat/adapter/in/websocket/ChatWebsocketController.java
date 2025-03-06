package com.codeit.side.chat.adapter.in.websocket;

import com.codeit.side.chat.adapter.in.websocket.request.ChatMessageReceived;
import com.codeit.side.chat.adapter.in.websocket.request.ChatMessageSend;
import com.codeit.side.chat.application.port.in.ChatMessageUseCase;
import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatType;
import com.codeit.side.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatWebsocketController {
    private final SimpMessagingTemplate messagingTemplate;

    private final ChatMessageUseCase chatMessageUseCase;

    @MessageMapping("/room/{id}/sendMessage")
    public void sendMessage(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @DestinationVariable Long id,
            @Payload ChatMessageReceived chatMessageReceived
    ) {
        User user = (User) sessionAttributes.get("user");
        chatMessageUseCase.findChatRoomBy(id, user.getId());

        ChatMessage chatMessage = ChatMessage.of(
                id,
                LocalDateTime.now(),
                user.getId(),
                user.getNickname(),
                ChatType.CHAT,
                chatMessageReceived.getContent()
        );
        String destination = "/topic/room/%s".formatted(id);

        messagingTemplate.convertAndSend(destination, ChatMessageSend.of(chatMessage, ""));
        chatMessageUseCase.save(chatMessage);
    }
}
