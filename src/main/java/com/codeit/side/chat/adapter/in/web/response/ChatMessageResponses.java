package com.codeit.side.chat.adapter.in.web.response;

import com.codeit.side.chat.domain.ChatMessages;

import java.util.List;

public record ChatMessageResponses(List<ChatMessageResponse> messages, boolean isLast) {
    public static ChatMessageResponses from(ChatMessages chatMessages) {
        List<ChatMessageResponse> chatMessageResponses = chatMessages.stream()
                .map(ChatMessageResponse::from)
                .toList();
        return new ChatMessageResponses(chatMessageResponses, chatMessages.isLast());
    }
}
