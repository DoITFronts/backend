package com.codeit.side.chat.application.service;

import com.codeit.side.chat.application.port.in.SaveChatMessageUseCase;
import com.codeit.side.chat.application.port.out.ChatMessageRepository;
import com.codeit.side.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class SaveChatMessageService implements SaveChatMessageUseCase {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }
}
