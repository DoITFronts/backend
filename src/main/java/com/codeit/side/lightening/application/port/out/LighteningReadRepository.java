package com.codeit.side.lightening.application.port.out;

import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.LighteningCondition;
import com.codeit.side.lightening.domain.LighteningLike;
import com.codeit.side.lightening.domain.LighteningMember;

import java.util.List;

public interface LighteningReadRepository {
    Lightening getById(Long lighteningId);

    boolean isJoined(String email, Long lighteningId);

    int countByLighteningId(Long id);

    List<LighteningMember> findAllMembersBy(Long id);

    boolean findLighteningLikeBy(String email, Long id);

    List<Lightening> findAllBy(LighteningCondition lighteningCondition, String email);

    List<LighteningMember> findAllMembersBy(List<Long> id);

    List<LighteningLike> findLighteningLikesBy(String email, List<Long> lighteningIds);
}
