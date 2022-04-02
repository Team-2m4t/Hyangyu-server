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
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Fair fair = fairRepository.findOne(fairId)
                .orElseThrow(() -> new CustomException(FAIR_NOT_FOUND));

        // 길이 확인
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            throw new CustomException(REVIEW_LENGTH_EXCESS);
        }

        //이미 박람회에 대한 리뷰 달았는지 확인 후 저장
        int count = fairReviewRepository.getCount(fair.getFairId(), user.getUserId());
        if (count == 0) {
            FairReview fairReview = FairReview.createFairReview(user, fair, LocalDateTime.now(), requestReviewDto.getContent(), requestReviewDto.getScore(), 0);
            FairReview savedFairReview = fairReviewRepository.save(fairReview);
        } else {
            throw new CustomException(ALREADY_SAVED_REVIEW);
        }
    }

    public void modifyFairReview(RequestReviewDto requestReviewDto, Long fairId) {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Fair fair = fairRepository.findOne(fairId)
                .orElseThrow(() -> new CustomException(FAIR_NOT_FOUND));

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
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Fair fair = fairRepository.findOne(fairId)
                .orElseThrow(() -> new CustomException(FAIR_NOT_FOUND));

        Optional<FairReview> fairReview = Optional.ofNullable(fairReviewRepository.getFairReview(fair.getFairId(), user.getUserId()));
        if (fairReview.isEmpty()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
        fairReview.ifPresent(fairReviewRepository::delete);
    }

    public Optional<FairReview> findReview(Long reviewId) {
        return fairReviewRepository.findById(reviewId);
    }

    public String accuseFairReview(FairReview fairReview, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (userId.equals(fairReview.getUser().getUserId())) {
            return "내가 쓴 리뷰는 신고할 수 없습니다.";
        }

        Optional<FairWarn> fairWarn = Optional.ofNullable(fairWarnRepository.getFairWarn(fairReview.getReviewId(), userId));
        if (fairWarn.isEmpty() && user.isPresent()) {
            FairWarn warn = FairWarn.createFairWarn(fairReview, user.get());
            fairWarnRepository.save(warn);

            fairReview.increaseWarn();
            if (fairReview.getWarn() == 3) {
                fairWarnRepository.deleteFairWarns(fairReview.getReviewId());
                fairReviewRepository.delete(fairReview);
                return "신고 3번이 누적되어 자동 삭제되었습니다.";
            }
        } else if (fairWarn.isPresent()) {
            return "이미 신고한 리뷰입니다.";
        }

        return "신고가 완료되었습니다.";
    }

    public List<MyReviewDto> getMyFairReviews(Long userId) {
        return fairReviewRepository.getMyFairReviews(userId);
    }

    public List<ReviewDto> getFairReviews(Long fairId) {
        return fairReviewRepository.getFairReviews(fairId);
    }
}
