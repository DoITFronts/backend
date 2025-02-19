package com.codeit.side.lightening.adapter.in.web;

import com.codeit.side.lightening.adapter.in.web.request.LighteningRequest;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public String create(@RequestPart MultipartFile image, @Valid  @RequestPart LighteningRequest lightening) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("email = " + email);
//        lighteningUseCase.save(lightening.toModel(hasImage(image)));
        return email;
    }

    private boolean hasImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            return true;
        }
        return false;
    }
}
