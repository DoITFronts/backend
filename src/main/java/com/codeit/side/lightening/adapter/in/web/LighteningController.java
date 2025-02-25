package com.codeit.side.lightening.adapter.in.web;

import com.codeit.side.common.adapter.exception.AuthenticationFailedException;
import com.codeit.side.common.adapter.exception.IllegalRequestException;
import com.codeit.side.lightening.adapter.in.web.request.LighteningRequest;
import com.codeit.side.lightening.adapter.in.web.response.CreateLighteningResponse;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import com.codeit.side.lightening.domain.Lightening;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lightenings")
public class LighteningController {
    private static final List<String> VALID_EXTENSIONS = List.of("jpg", "jpeg", "gif");
    private static final Long MAX_IMAGE_MB_SIZE = 5L;

    private final LighteningUseCase lighteningUseCase;

    @PostMapping
    public ResponseEntity<CreateLighteningResponse> create(
            @RequestPart(required = false) MultipartFile image,
            @Valid @RequestPart(name = "lightening") LighteningRequest lighteningRequest
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        validateImage(image);
        Lightening lighteningModel = lighteningRequest.toModel(hasImage(image));
        Lightening savedLightening = lighteningUseCase.save(email, lighteningModel, image);
        return ResponseEntity.ok(CreateLighteningResponse.from(savedLightening.getId()));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(email)) {
            throw new AuthenticationFailedException("로그인이 필요합니다.");
        }
        lighteningUseCase.like(email, id);
        return ResponseEntity.ok().build();
    }

    private boolean hasImage(MultipartFile image) {
        return image != null && !image.isEmpty();
    }

    private void validateImage(MultipartFile image) {
        if (image == null) {
            return;
        }
        if (!VALID_EXTENSIONS.contains(StringUtils.getFilenameExtension(image.getOriginalFilename()))) {
            throw new IllegalRequestException(String.format("이미지는 %s 형식이어야 합니다.", String.join(", ", VALID_EXTENSIONS)));
        }
        long size = image.getSize();
        if (size > 1024 * 1024 * MAX_IMAGE_MB_SIZE) {
            throw new IllegalRequestException("이미지 크기는 %sMB 이하여야 합니다.".formatted(MAX_IMAGE_MB_SIZE));
        }
    }
}
