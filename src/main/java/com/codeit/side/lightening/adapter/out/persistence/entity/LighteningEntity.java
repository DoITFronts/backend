package com.codeit.side.lightening.adapter.out.persistence.entity;

import com.codeit.side.common.adapter.out.persistence.entity.BaseEntity;
import com.codeit.side.lightening.domain.Category;
import com.codeit.side.lightening.domain.Lightening;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "min_capacity", nullable = false)
    private Integer minCapacity;

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "is_inactive", nullable = false)
    @ColumnDefault("false")
    private Boolean isInactive;

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
                .minCapacity(lightening.getMinCapacity())
                .placeName(lightening.getPlaceName())
                .latitude(lightening.getLatitude())
                .longitude(lightening.getLongitude())
                .build();
    }

    public static LighteningEntity of(Long id, String title, String summary, String description, String address, String city, String town, Category category, LocalDateTime targetAt, LocalDateTime endAt, Integer capacity, Boolean hasImage, String host, Integer minCapacity, String placeName, String latitude, String longitude) {
        return LighteningEntity.builder()
                .id(id)
                .title(title)
                .summary(summary)
                .description(description)
                .address(address)
                .city(city)
                .town(town)
                .category(category)
                .targetAt(targetAt)
                .endAt(endAt)
                .capacity(capacity)
                .hasImage(hasImage)
                .host(host)
                .minCapacity(minCapacity)
                .placeName(placeName)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public Lightening toDomain() {
        return Lightening.of(id, title, summary, description, address, city, town, category, targetAt, endAt, capacity, hasImage, host, minCapacity, placeName, latitude, longitude);
    }

    public void update(String description) {
        this.description = description;
    }

    public void delete() {
        this.isInactive = true;
    }
}
