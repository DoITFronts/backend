package com.codeit.side.lightening.application.port.out;

import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.LighteningMember;

import java.util.List;

public interface LighteningReadRepository {
    Lightening getById(Long lighteningId);

    boolean isJoined(String email, Long lighteningId);

    int countByLighteningId(Long id);

    List<LighteningMember> findAllMembersBy(Long id);

    boolean findLighteningLikeBy(String email, Long id);
}
