package com.codeit.side.lightening.application.service;

import com.codeit.side.common.application.port.out.FileUploadOutputPort;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.domain.Lightening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LighteningService implements LighteningUseCase {
    private final LighteningCommandRepository lighteningCommandRepository;
    private final FileUploadOutputPort fileUploader;

    public Lightening save(String email, Lightening lightening, MultipartFile image) {
        Lightening savedLightening = lighteningCommandRepository.save(email, lightening);
        fileUploader.uploadImageToS3(image, "lightening/" + savedLightening.getId(), "image.jpg", "jpg");
        return savedLightening;
    }
}
