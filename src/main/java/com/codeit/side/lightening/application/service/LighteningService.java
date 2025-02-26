package com.codeit.side.lightening.application.service;

import com.codeit.side.common.adapter.exception.LighteningAlreadyFullException;
import com.codeit.side.common.adapter.exception.UserAlreadyJoinedException;
import com.codeit.side.common.adapter.exception.UserNotJoinedException;
import com.codeit.side.common.application.port.out.FileUploadOutputPort;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningMemberEntityQueryRepository;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.application.port.out.LighteningReadRepository;
import com.codeit.side.lightening.domain.Lightening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LighteningService implements LighteningUseCase {
    private final LighteningCommandRepository lighteningCommandRepository;
    private final LighteningReadRepository lighteningReadRepository;
    private final FileUploadOutputPort fileUploader;
    private final LighteningMemberEntityQueryRepository lighteningMemberEntityQueryRepository;

    @Override
    @Transactional
    public Lightening save(String email, Lightening lightening, MultipartFile image) {
        Lightening savedLightening = lighteningCommandRepository.save(email, lightening);
        fileUploader.uploadImageToS3(image, "lightening/" + savedLightening.getId(), "image.jpg", "jpg");
        return savedLightening;
    }

    @Override
    @Transactional
    public void like(String email, Long id) {
        Lightening lightening = lighteningReadRepository.getById(id);
        lighteningCommandRepository.like(email, lightening.getId());
    }

    @Override
    @Transactional
    public void join(String email, Long id) {
        Lightening lightening = lighteningReadRepository.getById(id);
        validateLightening(email, lightening);
        lighteningCommandRepository.join(email, lightening.getId());
    }

    @Override
    @Transactional
    public void leave(String email, Long id) {
        Lightening lightening = lighteningReadRepository.getById(id);
        if (!lighteningReadRepository.isJoined(email, lightening.getId())) {
            throw new UserNotJoinedException(lightening.getId());
        }
        lighteningCommandRepository.leave(email, lightening.getId());
    }

    private void validateLightening(String email, Lightening lightening) {
        if (lighteningReadRepository.isJoined(email, lightening.getId())) {
            throw new UserAlreadyJoinedException("이미 참여한 번개입니다. id: %s, email: %s".formatted(lightening.getId(), email));
        }

        int currentMemberCount = lighteningMemberEntityQueryRepository.countByLighteningId(lightening.getId());
        if (lightening.isNotJoinable(currentMemberCount)) {
            throw new LighteningAlreadyFullException(lightening.getId());
        }
    }
}
