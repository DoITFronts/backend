package com.codeit.side.mock;

import com.codeit.side.user.application.port.out.UserCommandRepository;
import com.codeit.side.user.domain.User;

import java.util.ArrayList;
import java.util.List;

public class FakeUserCommandRepository implements UserCommandRepository {
    private List<User> users = new ArrayList<>();

    public FakeUserCommandRepository() {
    }

    public FakeUserCommandRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public User saveUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(String email, String description, String nickname, boolean hasImage) {
        return null;
    }
}
