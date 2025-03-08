package com.codeit.side.lightening.domain;

import com.codeit.side.common.adapter.exception.IllegalRequestException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum LighteningOrder {
    CREATED_DESC,
    TARGET_DATE_DESC,
    ;

    private static final Map<String, LighteningOrder> NAME_TO_LIGHTENING_ORDER = Arrays.stream(values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));

    public static LighteningOrder from(String lighteningOrder) {
        if (lighteningOrder != null && NAME_TO_LIGHTENING_ORDER.containsKey(lighteningOrder.toUpperCase())) {
            return NAME_TO_LIGHTENING_ORDER.get(lighteningOrder.toUpperCase());
        }
        throw new IllegalRequestException("잘못된 orderType입니다. orderType: " + lighteningOrder);
    }
}
