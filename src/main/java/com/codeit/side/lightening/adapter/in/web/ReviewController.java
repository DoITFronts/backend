package com.codeit.side.lightening.adapter.in.web;

import com.codeit.side.common.adapter.exception.AuthenticationFailedException;
import com.codeit.side.lightening.adapter.in.web.request.ReviewRequest;
import com.codeit.side.lightening.adapter.in.web.response.ReviewCreateResponse;
import com.codeit.side.lightening.adapter.in.web.response.ReviewResponses;
import com.codeit.side.lightening.application.port.in.ReviewUseCase;
import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.Reviews;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/lightenings/{id}/reviews")
public class ReviewController {
    private final ReviewUseCase reviewUseCase;

    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createReview(@PathVariable Long id, @RequestBody @Valid ReviewRequest reviewRequest) {
        String email = getEmail(true);
        Review review = reviewUseCase.create(email, id, reviewRequest.toCommand());
        return ResponseEntity.ok(ReviewCreateResponse.from(review.getId()));
    }

    @GetMapping
    public ResponseEntity<ReviewResponses> getReviews(@PathVariable Long id, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer page) {
        Reviews reviews = reviewUseCase.getReviews(id, page, size);
        return ResponseEntity.ok(ReviewResponses.from(reviews));
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
}
