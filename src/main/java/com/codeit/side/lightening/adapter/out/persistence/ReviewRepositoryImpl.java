package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.lightening.adapter.out.persistence.entity.ReviewEntity;
import com.codeit.side.lightening.adapter.out.persistence.jpa.ReviewJpaQueryRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.ReviewJpaRepository;
import com.codeit.side.lightening.application.port.out.ReviewRepository;
import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.command.ReviewCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewJpaRepository reviewJpaRepository;
    private final ReviewJpaQueryRepository reviewJpaQueryRepository;

    @Override
    public Review save(ReviewCommand reviewCommand) {
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
}
