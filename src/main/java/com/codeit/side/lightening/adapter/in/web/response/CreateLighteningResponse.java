package com.codeit.side.lightening.adapter.in.web.response;

public record CreateLighteningResponse(long id) {
    public static CreateLighteningResponse from(long id) {
        return new CreateLighteningResponse(id);
    }
}
