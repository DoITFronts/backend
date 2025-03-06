package com.codeit.side.chat.application.service;

import com.codeit.side.chat.application.port.in.ChatMessageUseCase;
import com.codeit.side.chat.application.port.out.ChatMemberRepository;
import com.codeit.side.chat.application.port.out.ChatMessageRepository;
import com.codeit.side.chat.application.port.out.ChatRoomRepository;
import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import com.codeit.side.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Primary
@Service
@RequiredArgsConstructor
public class ChatMessageService implements ChatMessageUseCase {
    private final ChatMessageRepository chatMessageRepository;
    private final UserQueryRepository userQueryRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;

    @Override
    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    @Override
    public void joinChatRoom(String email, ChatRoomCommand chatRoomCommand) {
        User host = userQueryRepository.getByEmail(email)
                .toDomain();
        List<User> users = userQueryRepository.findByIds(chatRoomCommand.getUserIds());
        List<Long> userIds = users.stream()
                .map(User::getId)
                .toList();
        ChatRoomCommand chatRoom = ChatRoomCommand.of(chatRoomCommand.getName(), host.getId(), userIds);
        Long chatRoomId = chatRoomRepository.save(chatRoom);
        chatMemberRepository.save(chatRoomId, chatRoom);
    }

    @Override
    public List<ChatRoom> findAllChatRooms(String email) {
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUserId(user.getId());
        Map<Long, List<ChatRoom>> idToChatRooms = chatRooms.stream()
                .collect(groupingBy(ChatRoom::getId));
        return chatRooms;
    }
}
