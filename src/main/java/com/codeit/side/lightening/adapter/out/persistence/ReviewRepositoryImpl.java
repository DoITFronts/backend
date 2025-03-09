package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.common.adapter.exception.AlreadyReviewedLighteningException;
import com.codeit.side.lightening.adapter.out.persistence.entity.ReviewEntity;
import com.codeit.side.lightening.adapter.out.persistence.jpa.ReviewJpaQueryRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.ReviewJpaRepository;
import com.codeit.side.lightening.application.port.out.ReviewRepository;
import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.command.ReviewCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewJpaRepository reviewJpaRepository;
    private final ReviewJpaQueryRepository reviewJpaQueryRepository;

    @Override
    public Review save(ReviewCommand reviewCommand) {
        if (reviewJpaQueryRepository.existsByLighteningIdAndUserId(reviewCommand.getLighteningId(), reviewCommand.getUserId())) {
            throw new AlreadyReviewedLighteningException();
        }
        ReviewEntity reviewEntity = ReviewEntity.from(reviewCommand);
        return reviewJpaRepository.save(reviewEntity)
                .toDomain();
    }

    @Override
    public List<Review> findAllByLighteningId(Long id, Integer page, Integer size) {
        return reviewJpaQueryRepository.findAllByLighteningId(id, page, size)
                .stream()
                .map(ReviewEntity::toDomain)
                .toList();
    }

    @Override
    public int getTotalCountByLighteningId(Long id) {
        return reviewJpaQueryRepository.getTotalCountByLighteningId(id);
    }

    @Override
    public List<Review> findAllBy(Long userId, String category, Integer size, Integer page) {
        return reviewJpaQueryRepository.findAllBy(userId, category, size, page)
                .stream()
                .map(ReviewEntity::toDomain)
                .toList();
    }

    @Override
    public int countAllBy(Long userId, String category) {
        return reviewJpaQueryRepository.countAllBy(userId, category);
    }

    @Override
    public List<Review> findAllBy(String category, String city, String town, LocalDateTime targetAt, String order, Integer size, Integer page) {
        return reviewJpaQueryRepository.findAllBy(category, city, town, targetAt, order, size, page)
                .stream()
                .map(ReviewDto::toDomain)
                .toList();
    }

    @Override
    public int countAllBy(String category, String city, String town, LocalDateTime targetAt, String order) {
        return reviewJpaQueryRepository.countAllBy(category, city, town, targetAt, order);
    }
}
