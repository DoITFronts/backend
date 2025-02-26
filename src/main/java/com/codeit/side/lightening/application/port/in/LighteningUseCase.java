package com.codeit.side.lightening.application.port.in;

import com.codeit.side.lightening.domain.Lightening;
import org.springframework.web.multipart.MultipartFile;

public interface LighteningUseCase {
    Lightening save(String email, Lightening lightening, MultipartFile image);

    void like(String email, Long id);

    void join(String email, Long id);

    void leave(String email, Long id);
}
