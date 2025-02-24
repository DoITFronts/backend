package com.codeit.side.lightening.adapter.in.web;

import com.codeit.side.lightening.adapter.in.web.request.LighteningRequest;
import com.codeit.side.lightening.adapter.in.web.response.CreateLighteningResponse;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import com.codeit.side.lightening.domain.Lightening;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lightening")
public class LighteningController {
    private final LighteningUseCase lighteningUseCase;

    @PostMapping
    public ResponseEntity<CreateLighteningResponse> create(
            @RequestPart MultipartFile image,
            @Valid @RequestPart(name = "lightening") LighteningRequest lighteningRequest
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Lightening lighteningModel = lighteningRequest.toModel(hasImage(image));
        Lightening savedLightening = lighteningUseCase.save(email, lighteningModel);
        return ResponseEntity.ok(CreateLighteningResponse.from(savedLightening.getId()));
    }

    private boolean hasImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            return true;
        }
        return false;
    }
}
