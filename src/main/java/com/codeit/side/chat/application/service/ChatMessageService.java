package com.codeit.side.chat.application.service;

import com.codeit.side.chat.application.port.in.ChatMessageUseCase;
import com.codeit.side.chat.application.port.out.ChatMemberRepository;
import com.codeit.side.chat.application.port.out.ChatMessageRepository;
import com.codeit.side.chat.application.port.out.ChatRoomRepository;
import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatMessages;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.ChatRoomInfo;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import com.codeit.side.common.adapter.exception.IllegalRequestException;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import com.codeit.side.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Primary
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageService implements ChatMessageUseCase {
    private final ChatMessageRepository chatMessageRepository;
    private final UserQueryRepository userQueryRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;

    @Override
    @Transactional
    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    @Override
    @Transactional
    public ChatRoom createChatRoom(String email, String chatRoomName) {
        User host = userQueryRepository.getByEmail(email)
                .toDomain();
        ChatRoomCommand userChatRoomCommand = ChatRoomCommand.of(chatRoomName, host.getId());
        ChatRoom chatRoom = chatRoomRepository.save(userChatRoomCommand);
        chatMemberRepository.save(chatRoom.getId(), userChatRoomCommand);
        return chatRoom;
    }

    @Override
    public List<ChatRoomInfo> findAllChatRooms(String email) {
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUserId(user.getId());
        List<Long> chatRoomIds = chatRooms.stream()
                .map(ChatRoom::getId)
                .toList();
        Map<Long, ChatMessage> allLastMessageByIds = chatMessageRepository.findAllLastMessageByIds(chatRoomIds);
        Map<Long, Integer> idToMemberSize = chatMemberRepository.findAllMemberCountByIds(chatRoomIds);
        return createChatRoomInfos(chatRooms, allLastMessageByIds, idToMemberSize);
    }

    @Override
    public void findChatRoomBy(Long id, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.getBy(id);
        if (!chatMemberRepository.existsByChatRoomIdAndUserId(chatRoom.getId(), userId)) {
            throw new IllegalRequestException("채팅방에 참여하지 않은 사용자입니다.");
        }
    }

    @Override
    public ChatMessages findAllMessagesByRoomId(Long roomId, String email, Long offset, Integer size) {
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        findChatRoomBy(roomId, user.getId());
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByRoomId(roomId, offset, size);
        boolean isLast = chatMessages.size() <= size;
        List<ChatMessage> messages = chatMessages.stream()
                .limit(size)
                .toList();
        return ChatMessages.of(messages, isLast);
    }

    @Override
    @Transactional
    public void joinChatRoom(Long id, String email) {
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        ChatRoom chatRoom = chatRoomRepository.getBy(id);
        if (chatMemberRepository.existsByChatRoomIdAndUserId(chatRoom.getId(), user.getId())) {
            throw new IllegalRequestException("이미 채팅방에 참여하고 있는 사용자입니다.");
        }
        chatMemberRepository.join(id, user.getId());
    }

    private List<ChatRoomInfo> createChatRoomInfos(List<ChatRoom> chatRooms, Map<Long, ChatMessage> allLastMessageByIds, Map<Long, Integer> idToMemberSize) {
        return chatRooms.stream()
                .map(chatRoom -> createChatRoomInfo(chatRoom, allLastMessageByIds.get(chatRoom.getId()), idToMemberSize.get(chatRoom.getId())))
                .toList();
    }

    private ChatRoomInfo createChatRoomInfo(ChatRoom chatRoom, ChatMessage chatMessage, Integer integer) {
        return ChatRoomInfo.of(chatRoom, chatMessage, integer);
    }
}
