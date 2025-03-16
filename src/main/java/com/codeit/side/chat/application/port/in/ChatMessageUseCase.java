package com.codeit.side.chat.application.port.in;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatMessages;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.ChatRoomInfo;

import java.util.List;

public interface ChatMessageUseCase {
    void save(ChatMessage chatMessage);

    ChatRoom createChatRoom(String email, Long lighteningId, String chatRoomName);

    List<ChatRoomInfo> findAllChatRooms(String email);

    void findChatRoomBy(Long id, Long userId);

    ChatMessages findAllMessagesByRoomId(Long roomId, String email, Long offset, Integer size);

    void joinChatRoom(Long id, String email);

    ChatRoom getChatRoomByLighteningId(Long lighteningId);

    List<ChatRoom> findAllChatRoomsByLighteningIds(List<Long> lighteningIds);

    void read(Long roomId, Long userId);

    void leaveChatRoom(Long id, String email);

    void joinChatRoomByLighteningId(Long id, String email);
}
