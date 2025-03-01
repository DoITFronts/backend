package com.codeit.side.lightening.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningPaging {
    private final int page;
    private final int size;

    public static LighteningPaging of(int page, int size){
        return new LighteningPaging(page, size);
    }

    public int getOffset() {
        return (page - 1) * size;
    }
}
