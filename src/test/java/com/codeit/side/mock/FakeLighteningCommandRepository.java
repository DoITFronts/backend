package com.codeit.side.mock;

import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.LighteningFixture;

import java.util.List;

public class FakeLighteningCommandRepository implements LighteningCommandRepository {
    private final List<Lightening> lightenings;
    private Long id = 1L;

    public FakeLighteningCommandRepository(List<Lightening> lightenings) {
        this.lightenings = lightenings;
    }

    @Override
    public Lightening save(String email, Lightening lightening) {
        Lightening save = LighteningFixture.create(id++, lightening);
        lightenings.add(save);
        return save;
    }
}
