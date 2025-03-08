package com.codeit.side.lightening.application.port.out;

import com.codeit.side.lightening.domain.Lightening;

public interface LighteningCommandRepository {
    Lightening save(String email, Lightening lightening);

    void like(String email, Long id);

    void join(String email, Long id);

    void leave(String email, Long id);

    void update(Long id, String description);
}
