package com.codeit.side.lightening.adapter.in.web.request;

import com.codeit.side.lightening.domain.command.ReviewCommand;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ReviewRequest(
        @NotNull(message = "리뷰 점수는 필수입니다.") @Min(value = 1, message = "리뷰 점수는 1~5점 입니다.") @Max(value = 5, message = "리뷰 점수는 1~5점 입니다.") Integer score,
        @NotNull(message = "리뷰 내용은 필수입니다.") @Length(max = 50, message = "리뷰는 최대 50자 입력 가능합니다.") String content
) {
    public ReviewCommand toCommand() {
        return ReviewCommand.of(score, content);
    }
}
