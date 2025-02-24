package com.codeit.side.lightening.application.service;

import com.codeit.side.common.adapter.out.storage.fileUploadAdapter;
import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.LighteningFixture;
import com.codeit.side.mock.FakeLighteningCommandRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class LighteningServiceTest {
    @Test
    @DisplayName("라이트닝 서비스는 라이트닝을 저장한 후 id가 있는 객체를 응답한다.")
    void save_success() {
        //given
        ArrayList<Lightening> lightenings = new ArrayList<>();
        FakeLighteningCommandRepository lighteningCommandRepository = new FakeLighteningCommandRepository(lightenings);
        LighteningService lighteningService = new LighteningService(lighteningCommandRepository, new fileUploadAdapter(null));
        Lightening lightening = LighteningFixture.create();
        String email = "bht9011@gmail.com";
        //when
        Lightening actual = lighteningService.save(email, lightening, null);
        //then
        Assertions.assertThat(actual.getId()).isEqualTo(1L);
    }
}