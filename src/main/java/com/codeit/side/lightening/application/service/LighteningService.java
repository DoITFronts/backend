package com.codeit.side.lightening.application.service;

import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.domain.Lightening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LighteningService {
    private final LighteningCommandRepository lighteningCommandRepository;

    public Lightening save(Lightening lightening){
        return lighteningCommandRepository.save(lightening);
    }
}
