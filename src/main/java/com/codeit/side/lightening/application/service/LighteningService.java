package com.codeit.side.lightening.application.service;

import com.codeit.side.common.application.port.out.FileUploadOutputPort;
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
}
