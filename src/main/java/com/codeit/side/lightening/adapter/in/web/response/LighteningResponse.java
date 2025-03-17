package com.codeit.side.lightening.adapter.in.web.response;

import com.codeit.side.lightening.domain.Category;
import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.LighteningInfo;

import java.time.LocalDateTime;
import java.util.List;

public record LighteningResponse(
        Long id,
        Category category,
        String address,
        String city,
        String town,
        String placeName,
        String latitude,
        String longitude,
        LocalDateTime targetAt,
        LocalDateTime endAt,
        String title,
        String summary,
        String description,
        String imageUrl,
        boolean isLiked,
        boolean isJoined,
        int capacity,
        int minCapacity,
        int participantCount,
        boolean isConfirmed,
        boolean isCompleted,
        Long chatRoomId,
        Integer unreadCount,
        List<LighteningMemberResponse> participants
) {
    public static LighteningResponse from(String email, LighteningInfo lighteningInfo) {
        Lightening lightening = lighteningInfo.getLightening();
        List<LighteningMemberResponse> lighteningMemberResponse = lighteningInfo.getLighteningMembers()
                .stream()
                .map(member -> LighteningMemberResponse.from(member, lightening.getHostEmail()))
                .toList();

        return new LighteningResponse(
                lightening.getId(),
                lightening.getCategory(),
                lightening.getAddress(),
                lightening.getCity(),
                lightening.getTown(),
                lightening.getPlaceName(),
                lightening.getLatitude(),
                lightening.getLongitude(),
                lightening.getTargetAt(),
                lightening.getEndAt(),
                lightening.getTitle(),
                lightening.getSummary(),
                lightening.getDescription(),
                lightening.getHasImage() ? "https://codeit-doit.s3.ap-northeast-2.amazonaws.com/lightening/%s/image.jpg".formatted(lightening.getId()) : "",
                lighteningInfo.isLiked(),
                lighteningMemberResponse.stream().anyMatch(member -> member.email().equals(email)),
                lightening.getCapacity(),
                lightening.getMinCapacity(),
                lighteningInfo.getLighteningMembers().size(),
                lighteningMemberResponse.size() >= lightening.getMinCapacity(),
                lighteningMemberResponse.size() >= lightening.getCapacity(),
                lighteningInfo.getChatRoom().getId(),
                lighteningInfo.getUnreadCount(),
                lighteningMemberResponse
        );
    }
}
