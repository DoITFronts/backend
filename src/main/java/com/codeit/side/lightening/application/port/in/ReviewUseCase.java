package com.codeit.side.lightening.application.port.in;

import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.Reviews;
import com.codeit.side.lightening.domain.command.ReviewCommand;
import com.codeit.side.user.domain.ReviewInfos;

import java.time.LocalDateTime;

public interface ReviewUseCase {
    Review create(String email, Long id, ReviewCommand reviewCommand);

    Reviews getReviews(Long id, Integer page, Integer size);

    ReviewInfos findAllBy(String email, String category, Integer size, Integer page);

    ReviewInfos findAllBy(String category, String city, String town, LocalDateTime targetAt, String order, Integer size, Integer page);
}
