package com.codeit.side.lightening.application.port.in;

import com.codeit.side.lightening.domain.Lightening;

public interface LighteningUseCase {
    Lightening save(String email, Lightening lightening);
}
