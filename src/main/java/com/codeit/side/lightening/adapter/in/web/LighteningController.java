package com.codeit.side.lightening.adapter.in.web;

import com.codeit.side.common.adapter.exception.AuthenticationFailedException;
import com.codeit.side.common.adapter.exception.IllegalRequestException;
import com.codeit.side.lightening.adapter.in.web.request.LighteningRequest;
import com.codeit.side.lightening.adapter.in.web.response.CreateLighteningResponse;
import com.codeit.side.lightening.adapter.in.web.response.LighteningResponse;
import com.codeit.side.lightening.adapter.in.web.response.LighteningResponses;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.LighteningCondition;
import com.codeit.side.lightening.domain.LighteningInfo;
import com.codeit.side.lightening.domain.LighteningPaging;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lightenings")
public class LighteningController {
    private static final List<String> VALID_EXTENSIONS = List.of("jpg", "jpeg", "gif", "png");
    private static final Long MAX_IMAGE_MB_SIZE = 5L;

    private final LighteningUseCase lighteningUseCase;

    @PostMapping
    public ResponseEntity<CreateLighteningResponse> create(
            @RequestPart(required = false) MultipartFile image,
            @Valid @RequestPart(name = "lightening") LighteningRequest lighteningRequest
    ) {
        String email = getEmail(true);
        validateImage(image);
        Lightening lighteningModel = lighteningRequest.toModel(hasImage(image));
        Lightening savedLightening = lighteningUseCase.save(email, lighteningModel, image);
        return ResponseEntity.ok(CreateLighteningResponse.from(savedLightening.getId()));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable Long id) {
        String email = getEmail(true);
        lighteningUseCase.like(email, id);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Void> join(@PathVariable Long id) {
        String email = getEmail(true);
        lighteningUseCase.join(email, id);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{id}/join")
    public ResponseEntity<Void> leave(@PathVariable Long id) {
        String email = getEmail(true);
        lighteningUseCase.leave(email, id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LighteningResponse> getLightening(@PathVariable Long id) {
        String email = getEmail(false);
        LighteningInfo lighteningInfo = lighteningUseCase.getById(email, id);
        return ResponseEntity.ok(LighteningResponse.from(email, lighteningInfo));
    }

    @GetMapping
    public ResponseEntity<LighteningResponses> getLightenings(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String town,
            @RequestParam(required = false) LocalDateTime targetAt,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        String email = getEmail(false);
        LighteningCondition lighteningCondition = LighteningCondition.of(category, city, town, targetAt, LighteningPaging.of(page, size));
        List<LighteningInfo> lighteningInfos = lighteningUseCase.findAllBy(email, lighteningCondition);
        return ResponseEntity.ok(LighteningResponses.from(email, lighteningInfos));
    }

    private String getEmail(boolean required) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (required && "anonymousUser".equals(email)) {
            throw new AuthenticationFailedException("로그인이 필요합니다.");
        }
        if ("anonymousUser".equals(email)) {
            return "";
        }
        return email;
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
