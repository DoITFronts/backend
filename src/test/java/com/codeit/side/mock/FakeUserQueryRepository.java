package com.codeit.side.mock;

import com.codeit.side.user.adapter.out.persistence.entity.UserEntity;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import com.codeit.side.user.domain.User;

import java.util.ArrayList;
import java.util.List;

public class FakeUserQueryRepository implements UserQueryRepository {
    public List<User> users = new ArrayList<>();

    public FakeUserQueryRepository() {
    }

    public FakeUserQueryRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public UserEntity getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAllByIds(List<Long> ids) {
        return List.of();
    }

    @Override
    public List<User> findAllByIds(List<Long> userIds) {
        return List.of();
    }
}
