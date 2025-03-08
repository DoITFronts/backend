package com.codeit.side.user.adapter.out.persistence.entity;

import com.codeit.side.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "nickname", length = 10, nullable = false)
    private String nickname;

    @Column(name = "name", length = 5, nullable = false)
    private String name;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "description")
    private String description;

    @Column(name = "has_image", nullable = false)
    @ColumnDefault("false")
    private Boolean hasImage;

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .name(user.getName())
                .birth(user.getBirth())
                .build();
    }

    public User toDomain() {
        return User.of(id, email, password, name, nickname, birth, description, hasImage);
    }

    public UserEntity update(String description, boolean hasImage) {
        this.description = description;
        if (hasImage) {
            this.hasImage = true;
        }
        return this;
    }
}
