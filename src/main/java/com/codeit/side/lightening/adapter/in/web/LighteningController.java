package com.codeit.side.lightening.adapter.in.web;

import com.codeit.side.common.adapter.exception.AuthenticationFailedException;
import com.codeit.side.lightening.adapter.in.web.request.LighteningLikesRequest;
import com.codeit.side.lightening.adapter.in.web.request.LighteningRequest;
import com.codeit.side.lightening.adapter.in.web.request.LighteningUpdateRequest;
import com.codeit.side.lightening.adapter.in.web.response.CreateLighteningResponse;
import com.codeit.side.lightening.adapter.in.web.response.LighteningResponse;
import com.codeit.side.lightening.adapter.in.web.response.LighteningResponses;
import com.codeit.side.lightening.application.port.in.LighteningUseCase;
import com.codeit.side.lightening.domain.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lightenings")
public class LighteningController {
    private final LighteningUseCase lighteningUseCase;

    @PostMapping
    public ResponseEntity<CreateLighteningResponse> create(
            @RequestPart(required = false) MultipartFile image,
            @Valid @RequestPart(name = "lightening") LighteningRequest lighteningRequest
    ) {
        String email = getEmail(true);
        Lightening lighteningModel = lighteningRequest.toModel(hasImage(image));
        LighteningChatRoom lighteningChatRoom = lighteningUseCase.save(email, lighteningModel, image);
        return ResponseEntity.ok(CreateLighteningResponse.from(lighteningChatRoom));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable Long id) {
        String email = getEmail(true);
        lighteningUseCase.like(email, id);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/like")
    public ResponseEntity<Void> likesAll(@RequestBody @Valid LighteningLikesRequest request) {
        String email = getEmail(true);
        lighteningUseCase.likesAll(email, request.lighteningIds());
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody LighteningUpdateRequest request) {
        String email = getEmail(true);
        lighteningUseCase.update(email, id, request.description());
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping
    public ResponseEntity<LighteningResponses> getLightenings(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String town,
            @RequestParam(required = false) LocalDateTime targetAt,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "CREATED_DESC") String order
    ) {
        String email = getEmail(false);
        LighteningCondition lighteningCondition = LighteningCondition.of(ConditionType.LIST, category, city, town, targetAt, LighteningPaging.of(page, size), order, null);
        List<LighteningInfo> lighteningInfos = lighteningUseCase.findAllBy(email, lighteningCondition);
        return ResponseEntity.ok(LighteningResponses.from(email, lighteningInfos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String email = getEmail(true);
        lighteningUseCase.delete(email, id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/like")
    public ResponseEntity<LighteningResponses> getLikeLightenings(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String town,
            @RequestParam(required = false) LocalDateTime targetAt,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "CREATED_DESC") String order
    ) {
        String email = getEmail(true);
        LighteningCondition lighteningCondition = LighteningCondition.of(ConditionType.LIKE, category, city, town, targetAt, LighteningPaging.of(page, size), order, null);
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
}
