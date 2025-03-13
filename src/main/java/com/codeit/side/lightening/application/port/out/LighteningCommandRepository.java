package com.codeit.side.lightening.application.port.out;

import com.codeit.side.lightening.domain.Lightening;

import java.util.Set;

public interface LighteningCommandRepository {
    Lightening save(String email, Lightening lightening);

    void like(String email, Long id);

    void join(String email, Long id);

    void leave(String email, Long id);

    void update(Long id, String description);

    void delete(Long id);

    void likesAll(String email, Set<Long> lighteningIds);
}
