package hyangyu.server.service;

import hyangyu.server.domain.*;
import hyangyu.server.dto.review.MyReviewDto;
import hyangyu.server.dto.review.RequestReviewDto;
import hyangyu.server.dto.review.ReviewDto;
import hyangyu.server.exception.CustomException;
import hyangyu.server.jwt.SecurityUtil;
import hyangyu.server.repository.FairRepository;
import hyangyu.server.repository.FairReviewRepository;
import hyangyu.server.repository.FairWarnRepository;
import hyangyu.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static hyangyu.server.constants.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FairReviewService {

    private final FairReviewRepository fairReviewRepository;
    private final FairWarnRepository fairWarnRepository;
    private final UserRepository userRepository;
    private final FairRepository fairRepository;

    public void saveFairReview(RequestReviewDto requestReviewDto, Long fairId) {
        User user = getUser();

        Fair fair = getFair(fairId);

        // 길이 확인
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            throw new CustomException(REVIEW_LENGTH_EXCESS);
        }

        //이미 박람회에 대한 리뷰 달았는지 확인 후 저장
        int count = fairReviewRepository.getCount(fair.getFairId(), user.getUserId());
        if (count == 0) {
            FairReview fairReview = FairReview.createFairReview(user, fair, LocalDateTime.now(), requestReviewDto.getContent(), requestReviewDto.getScore(), 0);
            fairReviewRepository.save(fairReview);
        } else {
            throw new CustomException(ALREADY_SAVED_REVIEW);
        }
    }

    public void modifyFairReview(RequestReviewDto requestReviewDto, Long fairId) {
        User user = getUser();

        Fair fair = getFair(fairId);

        // 길이 확인
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            throw new CustomException(REVIEW_LENGTH_EXCESS);
        }

        Optional<FairReview> fairReview = Optional.ofNullable(fairReviewRepository.getFairReview(fair.getFairId(), user.getUserId()));
        if (fairReview.isEmpty()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
        fairReview.ifPresent(review -> review.updateFairReview(requestReviewDto.getContent(), requestReviewDto.getScore()));
    }

    public void deleteFairReview(Long fairId) {
        User user = getUser();

        Fair fair = getFair(fairId);

        Optional<FairReview> fairReview = Optional.ofNullable(fairReviewRepository.getFairReview(fair.getFairId(), user.getUserId()));
        if (fairReview.isEmpty()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
        fairReview.ifPresent(fairReviewRepository::delete);
    }

    public void accuseFairReview(Long reviewId) {
        User user = getUser();

        FairReview fairReview = fairReviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        if (user.equals(fairReview.getUser())) {
            throw new CustomException(ACCUSE_DENIED);
        }

        Optional<FairWarn> fairWarn = fairWarnRepository.getFairWarn(reviewId, user.getUserId());
        if (fairWarn.isEmpty()) {
            FairWarn warn = FairWarn.createFairWarn(fairReview, user);
            fairWarnRepository.save(warn);

            fairReview.increaseWarn();
            if (fairReview.getWarn() == 3) {
                fairWarnRepository.deleteFairWarns(fairReview.getReviewId());
                fairReviewRepository.delete(fairReview);
            }
        }
        if (fairWarn.isPresent()) {
            throw new CustomException(ALREADY_WARN_REVIEW);
        }
    }

    public List<MyReviewDto> getMyFairReviews(Long userId) {
        return fairReviewRepository.getMyFairReviews(userId);
    }

    public List<ReviewDto> getFairReviews(Long fairId) {
        return fairReviewRepository.getFairReviews(fairId);
    }

    private User getUser() {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private Fair getFair(Long fairId) {
        return fairRepository.findOne(fairId)
                .orElseThrow(() -> new CustomException(FAIR_NOT_FOUND));
    }
}
