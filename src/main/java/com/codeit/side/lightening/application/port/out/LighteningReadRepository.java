package com.codeit.side.lightening.application.port.out;

import com.codeit.side.lightening.domain.Lightening;

public interface LighteningReadRepository {
    Lightening getById(Long lighteningId);
}
