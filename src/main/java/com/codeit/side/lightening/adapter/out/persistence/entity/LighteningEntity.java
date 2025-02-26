package com.codeit.side.lightening.adapter.out.persistence.entity;

import com.codeit.side.common.adapter.out.persistence.entity.BaseEntity;
import com.codeit.side.lightening.domain.Category;
import com.codeit.side.lightening.domain.Lightening;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lightenings")
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LighteningEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "town", nullable = false)
    private String town;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "target_at", nullable = false)
    private LocalDateTime targetAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "has_image", nullable = false)
    private Boolean hasImage;

    @Column(name = "host", nullable = false)
    private String host;

    public static LighteningEntity from(String email, Lightening lightening) {
        return LighteningEntity.builder()
                .title(lightening.getTitle())
                .summary(lightening.getSummary())
                .address(lightening.getAddress())
                .city(lightening.getCity())
                .town(lightening.getTown())
                .category(lightening.getCategory())
                .targetAt(lightening.getTargetAt())
                .endAt(lightening.getEndAt())
                .capacity(lightening.getCapacity())
                .hasImage(lightening.getHasImage())
                .host(email)
                .build();
    }

    public Lightening toDomain() {
        return Lightening.of(id, title, summary, address, city, town, category, targetAt, endAt, capacity, hasImage);
    }
}
