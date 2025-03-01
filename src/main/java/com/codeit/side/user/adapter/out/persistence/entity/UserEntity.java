package com.codeit.side.user.adapter.out.persistence.entity;

import com.codeit.side.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Builder(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
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
        return User.of(email, password, name, nickname, birth);
    }
}
