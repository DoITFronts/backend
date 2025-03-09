package com.codeit.side.lightening.application.port.out;

import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.command.ReviewCommand;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository {
    Review save(ReviewCommand reviewCommand);

    List<Review> findAllByLighteningId(Long id, Integer page, Integer size);

    int getTotalCountByLighteningId(Long id);

    List<Review> findAllBy(Long userId, String category, Integer size, Integer page);

    int countAllBy(Long userId, String category);

    List<Review> findAllBy(String category, String city, String town, LocalDateTime targetAt, String order, Integer size, Integer page);

    int countAllBy(String category, String city, String town, LocalDateTime targetAt, String order);
}
