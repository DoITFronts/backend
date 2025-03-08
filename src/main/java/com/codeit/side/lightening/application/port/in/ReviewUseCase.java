package com.codeit.side.lightening.application.port.in;

import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.Reviews;
import com.codeit.side.lightening.domain.command.ReviewCommand;

public interface ReviewUseCase {
    Review create(String email, Long id, ReviewCommand reviewCommand);

    Reviews getReviews(Long id, Integer page, Integer size);
}
