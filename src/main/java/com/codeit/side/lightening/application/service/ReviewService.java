package com.codeit.side.lightening.application.service;

import com.codeit.side.lightening.application.port.in.ReviewUseCase;
import com.codeit.side.lightening.application.port.out.LighteningReadRepository;
import com.codeit.side.lightening.application.port.out.ReviewRepository;
import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.Reviews;
import com.codeit.side.lightening.domain.UserReview;
import com.codeit.side.lightening.domain.command.ReviewCommand;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import com.codeit.side.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewUseCase {
    private final LighteningReadRepository lighteningReadRepository;
    private final ReviewRepository reviewRepository;
    private final UserQueryRepository userQueryRepository;

    @Override
    public Review create(String email, Long id, ReviewCommand reviewCommand) {
        User user = userQueryRepository.getByEmail(email)
                .toDomain();
        Lightening lightening = lighteningReadRepository.getById(id);

        return reviewRepository.save(ReviewCommand.of(lightening.getId(), user.getId(), reviewCommand.getScore(), reviewCommand.getContent()));
    }

    @Override
    public Reviews getReviews(Long id, Integer page, Integer size) {
        Lightening lightening = lighteningReadRepository.getById(id);
        List<Review> reviews = reviewRepository.findAllByLighteningId(lightening.getId(), page, size);
        int totalCount = reviewRepository.getTotalCountByLighteningId(lightening.getId());
        List<UserReview> userReviews = createUserReviews(reviews);
        return Reviews.of(userReviews, totalCount);
    }

    private List<UserReview> createUserReviews(List<Review> reviews) {
        List<Long> userIds = reviews.stream()
                .map(Review::getUserId)
                .toList();
        Map<Long, User> idToUser = userQueryRepository.findAllByIds(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        return reviews.stream()
                .map(review -> UserReview.of(review, idToUser.get(review.getUserId())))
                .toList();
    }
}
