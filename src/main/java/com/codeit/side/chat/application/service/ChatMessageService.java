package com.codeit.side.chat.application.service;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMemberEntity;
import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageReadEntity;
import com.codeit.side.chat.application.port.in.ChatMessageUseCase;
import com.codeit.side.chat.application.port.out.ChatMemberRepository;
import com.codeit.side.chat.application.port.out.ChatMessageReadRepository;
import com.codeit.side.chat.application.port.out.ChatMessageRepository;
import com.codeit.side.chat.application.port.out.ChatRoomRepository;
import com.codeit.side.chat.domain.*;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageService implements ChatMessageUseCase {
    private final ChatMessageRepository chatMessageRepository;
    private final UserQueryRepository userQueryRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMessageReadRepository chatMessageReadRepository;

    @Override
    @Transactional
    public void save(ChatMessage chatMessage) {
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        List<ChatMemberEntity> chatMemberEntities = chatMemberRepository.findAllMemberById(savedChatMessage.getRoomId());
        List<Long> userIds = chatMemberEntities.stream()
                .map(ChatMemberEntity::getUserId)
                .toList();
        chatMessageReadRepository.save(savedChatMessage.getRoomId(), userIds);
    }

    @Override
    @Transactional
    public ChatRoom createChatRoom(String email, Long lighteningId, String chatRoomName) {
        User host = userQueryRepository.getByEmail(email)
                .toDomain();
        ChatRoomCommand userChatRoomCommand = ChatRoomCommand.of(chatRoomName, lighteningId, host.getId());
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
        Map<Long, ChatMessage> idToChatMessage = chatMessageRepository.findAllLastMessageByIds(chatRoomIds);
        Map<Long, Integer> idToMemberSize = chatMemberRepository.findAllMemberCountByIds(chatRoomIds);
        return createChatRoomInfos(chatRooms, idToChatMessage, idToMemberSize);
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
        List<Long> userIds = extractUserIds(chatMessages);
        Map<Long, User> idToUser = userQueryRepository.findAllByIds(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        List<UserChatMessage> userChatMessages = messages.stream()
                .map(message -> UserChatMessage.of(message, idToUser.get(message.getUserId())))
                .toList();
        return ChatMessages.of(userChatMessages, isLast);
    }

    private List<Long> extractUserIds(List<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .map(ChatMessage::getUserId)
                .distinct()
                .toList();
    }

    @Override
    @Transactional
    public void joinChatRoom(Long id, String email) {
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        ChatRoom chatRoom = chatRoomRepository.getBy(id);
        chatMemberRepository.join(chatRoom.getId(), user.getId());
    }

    @Override
    @Transactional
    public void joinChatRoomByLighteningId(Long id, String email) {
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        ChatRoom chatRoom = chatRoomRepository.getByLighteningId(id);
        chatMemberRepository.join(chatRoom.getId(), user.getId());
    }

    @Override
    public Map<Long, Integer> countAllUnreadMessages(String email, List<Long> chatRoomIds) {
        if ("".equals(email)) {
            return Map.of();
        }
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        List<ChatMessageReadEntity> chatMessageReadEntities = chatMessageReadRepository.findAllUnreadMessages(user.getId(), chatRoomIds);
        return chatMessageReadEntities.stream()
                .collect(Collectors.groupingBy(ChatMessageReadEntity::getChatRoomId, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }

    @Override
    public int countUnreadMessages(Long chatRoomId, String email) {
        if ("".equals(email)) {
            return 0;
        }
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        return chatMessageReadRepository.findUnreadMessagesBy(chatRoomId, user.getId());
    }

    @Override
    public ChatRoom getChatRoomByLighteningId(Long lighteningId) {
        return chatRoomRepository.getByLighteningId(lighteningId);
    }

    @Override
    public List<ChatRoom> findAllChatRoomsByLighteningIds(List<Long> lighteningIds) {
        return chatRoomRepository.findAllByLighteningIds(lighteningIds);
    }

    @Override
    @Transactional
    public void read(Long roomId, Long userId) {
        chatMessageReadRepository.read(roomId, userId);
    }

    @Override
    @Transactional
    public void leaveChatRoom(Long id, String email) {
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        ChatRoom chatRoom = chatRoomRepository.getByLighteningId(id);
        chatMemberRepository.leave(chatRoom.getId(), user.getId());
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
