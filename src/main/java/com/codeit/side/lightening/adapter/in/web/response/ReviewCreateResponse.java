package com.codeit.side.lightening.adapter.in.web.response;

public record ReviewCreateResponse(long id) {
    public static ReviewCreateResponse from(long id) {
        return new ReviewCreateResponse(id);
    }
}
