package com.codeit.side.lightening.adapter.out.persistence.entity;

import com.codeit.side.common.adapter.out.persistence.entity.BaseEntity;
import com.codeit.side.lightening.domain.LighteningLike;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lightening_likes")
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LighteningLikeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lightening_id")
    private Long lighteningId;

    @Column(name = "email")
    private String email;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public static LighteningLikeEntity of(Long lighteningId, String email) {
        return LighteningLikeEntity.builder()
                .lighteningId(lighteningId)
                .email(email)
                .isDeleted(false)
                .build();
    }

    public void update() {
        isDeleted = !isDeleted;
    }

    public LighteningLike toDomain() {
        return LighteningLike.of(lighteningId, email);
    }
}
