package com.codeit.side.lightening.adapter.out.persistence.entity;

import com.codeit.side.common.adapter.out.persistence.entity.BaseEntity;
import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.command.ReviewCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    private String content;

    private Long lighteningId;

    private Long userId;

    public static ReviewEntity from(ReviewCommand reviewCommand) {
        return ReviewEntity.builder()
                .score(reviewCommand.getScore())
                .content(reviewCommand.getContent())
                .lighteningId(reviewCommand.getLighteningId())
                .userId(reviewCommand.getUserId())
                .build();
    }

    public Review toDomain() {
        return Review.of(id, lighteningId, userId, score, content, getCreatedAt());
    }
}
