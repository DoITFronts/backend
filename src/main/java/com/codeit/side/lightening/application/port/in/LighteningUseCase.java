package com.codeit.side.lightening.application.port.in;

import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.LighteningCondition;
import com.codeit.side.lightening.domain.LighteningInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LighteningUseCase {
    Lightening save(String email, Lightening lightening, MultipartFile image);

    void like(String email, Long id);

    void join(String email, Long id);

    void leave(String email, Long id);

    LighteningInfo getById(String email, Long id);

    List<LighteningInfo> findAllBy(String email, LighteningCondition lighteningCondition);
}
