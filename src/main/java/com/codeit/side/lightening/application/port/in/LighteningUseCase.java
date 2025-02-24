package com.codeit.side.lightening.application.port.in;

import com.codeit.side.lightening.domain.Lightening;
import org.springframework.web.multipart.MultipartFile;

public interface LighteningUseCase {
    Lightening save(String email, Lightening lightening, MultipartFile image);
}
