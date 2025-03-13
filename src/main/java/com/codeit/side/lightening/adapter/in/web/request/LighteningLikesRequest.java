package com.codeit.side.lightening.adapter.in.web.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record LighteningLikesRequest(
        @NotNull(message = "lighteningIds는 필수입니다.") Set<Long> lighteningIds
) {
}
