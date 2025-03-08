package com.codeit.side.lightening.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningCondition {
    private final ConditionType conditionType;
    private final String category;
    private final String city;
    private final String town;
    private final LocalDateTime targetAt;
    private final LighteningPaging lighteningPaging;
    private final String lighteningOrder;
    private final String createdBy;

    public static LighteningCondition of(
            ConditionType conditionType,
            String category,
            String city,
            String town,
            LocalDateTime targetAt,
            LighteningPaging lighteningPaging,
            String lighteningOrder,
            String createdBy
    ){
        return new LighteningCondition(conditionType, category, city, town, targetAt, lighteningPaging, lighteningOrder, createdBy);
    }

    public static LighteningCondition of(
            String category,
            ConditionType conditionType,
            LighteningPaging lighteningPaging,
            String lighteningOrder,
            String createdBy
    ) {
        return of(conditionType, category, null, null, null, lighteningPaging, lighteningOrder, createdBy);
    }

    public int getOffset() {
        return lighteningPaging.getOffset();
    }
}
