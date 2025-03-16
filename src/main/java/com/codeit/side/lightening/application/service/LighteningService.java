package com.codeit.side.lightening.application.service;

import com.codeit.side.chat.application.port.in.ChatMessageUseCase;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.common.adapter.exception.IllegalRequestException;
import com.codeit.side.common.adapter.exception.LighteningAlreadyFullException;
import com.codeit.side.common.adapter.exception.UserAlreadyJoinedException;
import com.codeit.side.common.adapter.exception.UserNotJoinedException;
import com.codeit.side.common.application.port.out.FileUploadOutputPort;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.application.port.out.LighteningReadRepository;
import com.codeit.side.lightening.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LighteningService implements LighteningUseCase {
    private final LighteningCommandRepository lighteningCommandRepository;
    private final LighteningReadRepository lighteningReadRepository;
    private final FileUploadOutputPort fileUploader;
    private final ChatMessageUseCase chatMessageUseCase;

    @Override
    @Transactional
    public LighteningChatRoom save(String email, Lightening lightening, MultipartFile image) {
        fileUploader.validateImage(image);
        Lightening savedLightening = lighteningCommandRepository.save(email, lightening);
        lighteningCommandRepository.join(email, savedLightening.getId());
        ChatRoom chatRoom = chatMessageUseCase.createChatRoom(email, savedLightening.getId(), savedLightening.getTitle());
        fileUploader.uploadImageToS3(image, "lightening/" + savedLightening.getId(), "image.jpg", "jpg");
        return LighteningChatRoom.of(savedLightening, chatRoom);
    }

    @Override
    @Transactional
    public void like(String email, Long id) {
        Lightening lightening = lighteningReadRepository.getById(id);
        lighteningCommandRepository.like(email, lightening.getId());
    }

    @Override
    @Transactional
    public void join(String email, Long id) {
        Lightening lightening = lighteningReadRepository.getById(id);
        validateLightening(email, lightening);
        lighteningCommandRepository.join(email, lightening.getId());
        chatMessageUseCase.joinChatRoomByLighteningId(id, email);
    }

    @Override
    @Transactional
    public void leave(String email, Long id) {
        Lightening lightening = lighteningReadRepository.getById(id);
        if (!lighteningReadRepository.isJoined(email, lightening.getId())) {
            throw new UserNotJoinedException(lightening.getId());
        }
        lighteningCommandRepository.leave(email, lightening.getId());
        chatMessageUseCase.leaveChatRoom(id, email);
    }

    @Override
    public LighteningInfo getById(String email, Long id) {
        Lightening lightening = lighteningReadRepository.getById(id);
        List<LighteningMember> lighteningMember = lighteningReadRepository.findAllMembersBy(lightening.getId());
        boolean isLighteningLike = lighteningReadRepository.findLighteningLikeBy(email, lightening.getId());
        ChatRoom chatRoom = chatMessageUseCase.getChatRoomByLighteningId(lightening.getId());
        return createLighteningInfo(lightening, lighteningMember, chatRoom, isLighteningLike);
    }

    @Override
    public List<LighteningInfo> findAllBy(String email, LighteningCondition lighteningCondition) {
        List<Lightening> lightenings = lighteningReadRepository.findAllBy(lighteningCondition, email);
        List<Long> lighteningIds = lightenings.stream()
                .map(Lightening::getId)
                .toList();
        List<LighteningMember> lighteningMembers = lighteningReadRepository.findAllMembersBy(lighteningIds);
        List<LighteningLike> lighteningLikes = lighteningReadRepository.findLighteningLikesBy(email, lighteningIds);
        List<ChatRoom> chatRoomIds = chatMessageUseCase.findAllChatRoomsByLighteningIds(lighteningIds);
        return createLighteningInfos(lightenings, lighteningMembers, chatRoomIds, lighteningLikes);
    }

    @Override
    @Transactional
    public void update(String email, Long id, String description) {
        Lightening lightening = lighteningReadRepository.getById(id);
        if (!lightening.getHostEmail().equals(email)) {
            throw new IllegalRequestException("해당 번개의 주최자가 아닙니다. lighteningId: %s, email: %s".formatted(id, email));
        }
        lighteningCommandRepository.update(id, description);
    }

    @Override
    @Transactional
    public void delete(String email, Long id) {
        Lightening lightening = lighteningReadRepository.getById(id);
        if (!lightening.getHostEmail().equals(email)) {
            throw new IllegalRequestException("해당 번개의 주최자가 아닙니다. lighteningId: %s, email: %s".formatted(id, email));
        }
        lighteningCommandRepository.delete(id);
    }

    @Override
    @Transactional
    public void likesAll(String email, Set<Long> lighteningIds) {
        List<Lightening> lightenings = lighteningReadRepository.findAllBy(new ArrayList<>(lighteningIds));
        if (lightenings.size() != lighteningIds.size()) {
            List<Long> extractIds = extractNotExistingLighteningIds(lightenings, new ArrayList<>(lighteningIds));
            throw new IllegalRequestException("존재하지 않는 번개가 포함되어 있습니다. lighteningIds: %s".formatted(extractIds));
        }
        lighteningCommandRepository.likesAll(email, lighteningIds);
    }

    private List<Long> extractNotExistingLighteningIds(List<Lightening> lightenings, List<Long> lighteningIds) {
        Set<Long> target = lightenings.stream()
                .map(Lightening::getId)
                .collect(Collectors.toSet());
        lighteningIds.removeAll(target);
        return lighteningIds;
    }

    private List<LighteningInfo> createLighteningInfos(List<Lightening> lightenings, List<LighteningMember> lighteningMembers, List<ChatRoom> chatRooms, List<LighteningLike> lighteningLikes) {
        Map<Long, List<LighteningMember>> idToLighteningMembers = lighteningMembers.stream()
                .collect(Collectors.groupingBy(LighteningMember::getLighteningId));
        Map<Long, LighteningLike> idToLighteningLike = lighteningLikes.stream()
                .collect(Collectors.toMap(LighteningLike::getLighteningId, Function.identity()));
        Map<Long, ChatRoom> idToChatRoom = chatRooms.stream()
                .collect(Collectors.toMap(ChatRoom::getLighteningId, Function.identity()));

        return lightenings.stream()
                .map(lightening -> createLighteningInfo(lightening, idToLighteningMembers.getOrDefault(lightening.getId(), List.of()), idToChatRoom.get(lightening.getId()), idToLighteningLike.containsKey(lightening.getId())))
                .toList();
    }

    private LighteningInfo createLighteningInfo(Lightening lightening, List<LighteningMember> lighteningMember, ChatRoom chatRoom, boolean isLighteningLike) {
        return LighteningInfo.of(lightening, lighteningMember, chatRoom, isLighteningLike);
    }

    private void validateLightening(String email, Lightening lightening) {
        if (lighteningReadRepository.isJoined(email, lightening.getId())) {
            throw new UserAlreadyJoinedException("이미 참여한 번개입니다. lighteningId: %s, email: %s".formatted(lightening.getId(), email));
        }

        int currentMemberCount = lighteningReadRepository.countByLighteningId(lightening.getId());
        if (lightening.isNotJoinable(currentMemberCount)) {
            throw new LighteningAlreadyFullException(lightening.getId());
        }
    }
}
