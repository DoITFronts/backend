package com.codeit.side.chat.adapter.in.websocket;

import com.codeit.side.chat.adapter.in.websocket.request.ChatMessageReceived;
import com.codeit.side.chat.adapter.in.websocket.request.ChatMessageSend;
import com.codeit.side.chat.application.port.in.SaveChatMessageUseCase;
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
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    private final SaveChatMessageUseCase saveChatMessageUseCase;
//    private final ChatHistoryUseCase chatHistoryUseCase;

    @MessageMapping("/room/{id}/sendMessage")
    public void sendMessage(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @DestinationVariable Long id,
            @Payload ChatMessageReceived chatMessageReceived
    ) {
        User user = (User) sessionAttributes.get("user");

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
        saveChatMessageUseCase.save(chatMessage);
    }

//    @MessageMapping("/room/recent")
//    @SendToUser("/queue/recent")
//    public List<ChatMessage> getRecentChats(
//            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes
//    ) {
//        User user = (User) sessionAttributes.get("user");
//
//        return chatHistoryUseCase.getRecentClubChatsForUser(user.getId());
//    }
//
//    @MessageMapping("/room/history/{id}")
//    @SendToUser("/queue/chatHistory")
//    public HistoryResponses getAllChatsByBookClubId(
//            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
//            @DestinationVariable Long id
//    ) {
//        User user = (User) sessionAttributes.get("user");
//
//        return HistoryResponses.from(chatHistoryUseCase.getAllClubChats(user.getId(), id));
//    }
}
