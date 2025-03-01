package com.codeit.side.lightening.adapter.in.web.response;

import com.codeit.side.lightening.domain.LighteningInfo;

import java.util.List;

public record LighteningResponses(List<LighteningResponse> lighteningResponses) {
    public static LighteningResponses from(String email, List<LighteningInfo> lighteningInfos) {
        return new LighteningResponses(
                lighteningInfos.stream()
                        .map(lighteningInfo -> LighteningResponse.from(email, lighteningInfo))
                        .toList()
        );
    }
}
