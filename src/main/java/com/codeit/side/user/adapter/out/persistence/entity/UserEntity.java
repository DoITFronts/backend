package com.codeit.side.user.adapter.out.persistence.entity;

import com.codeit.side.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
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
        return new UserEntity(
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getName(),
                user.getBirth()
        );
    }

    public User toDomain() {
        return User.of(email, password, name, nickname, birth);
    }
}
