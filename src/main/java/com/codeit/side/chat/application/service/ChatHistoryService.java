package com.codeit.side.chat.application.service;

//@Service
//@RequiredArgsConstructor
//public class ChatHistoryService implements ChatHistoryUseCase {
//
//    private final QueryBookClubPort queryBookClubPort;
//    private final LoadChatPort loadChatPort;
//
//    @Override
//    public List<ChatMessage> getRecentClubChatsForUser(Long userId) {
//        return queryBookClubPort.findUserJoinedBookClubs(userId, userId, OrderType.DESC, Pageable.ofSize(100), false)
//                .stream()
//                .map(Lightening::getId)
//                .map(loadChatPort::loadRecentChat)
//                .filter(m -> m.getBookClubId() != null)
//                .toList();
//    }
//
//    @Override
//    public List<ChatMessage> getAllClubChats(Long userId, Long bookClubId) {
//        boolean isJoined = queryBookClubPort.findUserJoinedBookClubs(userId, userId, OrderType.DESC, Pageable.ofSize(100), false)
//                .stream()
//                .map(BookClub::getId)
//                .anyMatch(id -> id.equals(bookClubId));
//
//        if(!isJoined) {
//            throw new UnauthorizedChatRoomAccessException("유저가 참여하지 않은 북클럽의 채팅 정보는 조회할 수 없습니다.");
//        }
//
//        return loadChatPort.loadAllChat(bookClubId);
//    }
//}
