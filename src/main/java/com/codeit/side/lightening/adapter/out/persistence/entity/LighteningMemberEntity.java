package com.codeit.side.lightening.adapter.out.persistence.entity;

import com.codeit.side.common.adapter.out.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lightening_members")
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LighteningMemberEntity extends BaseEntity {
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

    public static LighteningMemberEntity of(Long lighteningId, String email) {
        return LighteningMemberEntity.builder()
                .lighteningId(lighteningId)
                .email(email)
                .isDeleted(false)
                .build();
    }

    public void update() {
        isDeleted = !isDeleted;
    }

    public void delete() {
        isDeleted = true;
    }
}

