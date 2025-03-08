package com.codeit.side.common.adapter.exception;

public class AlreadyReviewedLighteningException extends BusinessException {
    public AlreadyReviewedLighteningException() {
        super(ErrorCode.ALREADY_REVIEWED, "이미 리뷰를 작성한 번개입니다.");
    }
}
