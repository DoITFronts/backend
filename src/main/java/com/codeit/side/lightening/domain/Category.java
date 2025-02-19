package com.codeit.side.lightening.domain;

import com.codeit.side.common.adapter.exception.IllegalRequestException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Category {
    ALCOHOL,
    CAFE,
    BOARD_GAME,
    GOURMET,
    ;

    private static final Map<String, Category> NAME_TO_CATEGORY = Arrays.stream(values())
            .collect(Collectors.toMap(Category::name, category -> category));

    public static Category from(String category) {
        if (category == null || !NAME_TO_CATEGORY.containsKey(category)) {
            throw new IllegalRequestException("Category로 변경이 불가능합니다. category: " + category);
        }
        return NAME_TO_CATEGORY.get(category);
    }
}
