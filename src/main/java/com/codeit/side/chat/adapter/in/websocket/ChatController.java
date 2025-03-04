package com.codeit.side.chat.adapter.in.websocket;

import com.codeit.side.chat.adapter.in.websocket.request.ChatMessageReceived;
import com.codeit.side.chat.adapter.in.websocket.request.ChatMessageSend;
import com.codeit.side.chat.application.port.in.ChatHistoryUseCase;
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
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    private final SaveChatMessageUseCase saveChatMessageUseCase;
    private final ChatHistoryUseCase chatHistoryUseCase;

    @MessageMapping("/group-chat/{chatRoomId}/sendMessage")
    public void sendMessage(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @DestinationVariable Long chatRoomId, //경로에서 추출
            @Payload ChatMessageReceived chatMessageReceived
    ) {
        User user = (User) sessionAttributes.get("user");

        ChatMessage chatMessage = new ChatMessage(
                chatRoomId,
                ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime(),
                user.getId(),
                user.getNickname(),
                ChatType.CHAT,
                chatMessageReceived.getContent()
        );

        String destination = "/topic/group-chat/" + chatRoomId;

        messagingTemplate.convertAndSend(destination, ChatMessageSend.from(chatMessage, user.getImage()));
        saveChatMessageUseCase.save(chatMessage);
    }

    @MessageMapping("/group-chat/recent")
    @SendToUser("/queue/recent")
    public List<ChatMessage> getRecentChats(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes
    ) {
        User user = (User) sessionAttributes.get("user");

        return chatHistoryUseCase.getRecentClubChatsForUser(user.getId());
    }

    @MessageMapping("/group-chat/history/{chatRoomId}")
    @SendToUser("/queue/chatHistory")
    public HistoryResponses getAllChatsByBookClubId(
            @Header(name = "simpSessionAttributes") Map<String, Object> sessionAttributes,
            @DestinationVariable Long chatRoomId //경로에서 추출
    ) {
        User user = (User) sessionAttributes.get("user");

        return HistoryResponses.from(chatHistoryUseCase.getAllClubChats(user.getId(), chatRoomId));
    }

}
