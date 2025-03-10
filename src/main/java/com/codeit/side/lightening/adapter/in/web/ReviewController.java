package com.codeit.side.lightening.adapter.in.web;

import com.codeit.side.common.adapter.exception.AuthenticationFailedException;
import com.codeit.side.lightening.adapter.in.web.request.ReviewRequest;
import com.codeit.side.lightening.adapter.in.web.response.ReviewCreateResponse;
import com.codeit.side.lightening.adapter.in.web.response.ReviewResponses;
import com.codeit.side.lightening.application.port.in.ReviewUseCase;
import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.Reviews;
import com.codeit.side.user.adapter.in.web.response.ReviewInfoResponses;
import com.codeit.side.user.domain.ReviewInfos;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {
    private final ReviewUseCase reviewUseCase;

    @PostMapping("/lightenings/{id}/reviews")
    public ResponseEntity<ReviewCreateResponse> createReview(@PathVariable Long id, @RequestBody @Valid ReviewRequest reviewRequest) {
        String email = getEmail(true);
        Review review = reviewUseCase.create(email, id, reviewRequest.toCommand());
        return ResponseEntity.ok(ReviewCreateResponse.from(review.getId()));
    }

    @GetMapping("/lightenings/{id}/reviews")
    public ResponseEntity<ReviewResponses> getReviews(@PathVariable Long id, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer page) {
        Reviews reviews = reviewUseCase.getReviews(id, page, size);
        return ResponseEntity.ok(ReviewResponses.from(reviews));
    }

    @GetMapping("/reviews/all")
    public ResponseEntity<ReviewInfoResponses> getReviews(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String town,
            @RequestParam(required = false)LocalDateTime targetAt,
            @RequestParam(required = false, defaultValue = "CREATED_DESC") String order,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        ReviewInfos reviewInfos = reviewUseCase.findAllBy(category, city, town, targetAt, order, size, page);
        return ResponseEntity.ok(ReviewInfoResponses.from(reviewInfos));
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
