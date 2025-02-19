package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.domain.Lightening;
import org.springframework.stereotype.Repository;

@Repository
public class LighteningCommandRepositoryImpl implements LighteningCommandRepository {

    @Override
    public Lightening save(Lightening lightening) {
        return null;
    }
}
