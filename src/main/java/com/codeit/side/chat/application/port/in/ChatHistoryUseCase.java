package com.codeit.side.chat.application.port.in;

import com.codeit.side.chat.domain.ChatMessage;

import java.util.List;

public interface ChatHistoryUseCase {
    List<ChatMessage> getRecentClubChatsForUser(Long userId);

    List<ChatMessage> getAllClubChats(Long userId, Long bookClubId);
}
