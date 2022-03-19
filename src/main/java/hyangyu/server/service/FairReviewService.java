package hyangyu.server.service;

import hyangyu.server.domain.Fair;
import hyangyu.server.domain.FairReview;
import hyangyu.server.domain.FairWarn;
import hyangyu.server.domain.User;
import hyangyu.server.dto.review.MyReviewDto;
import hyangyu.server.dto.review.RequestReviewDto;
import hyangyu.server.dto.review.ReviewDto;
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

@Service
@Transactional
@RequiredArgsConstructor
public class FairReviewService {

    private final FairReviewRepository fairReviewRepository;
    private final FairWarnRepository fairWarnRepository;
    private final UserRepository userRepository;
    private final FairRepository fairRepository;

    public int saveFairReview(Long userId, Long fairId, RequestReviewDto requestReviewDto) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Fair> fair = fairRepository.findOne(fairId);
        int count = fairReviewRepository.getCount(fair.get().getFairId(), user.get().getUserId());
        if (count == 0) {
            FairReview fairReview = FairReview.createFairReview(user.get(), fair.get(), LocalDateTime.now(), requestReviewDto.getContent(), requestReviewDto.getScore(), 0);
            FairReview savedFairReview = fairReviewRepository.save(fairReview);
        }
        return count;
    }

    public Optional<FairReview> modifyFairReview(Long userId, Long fairId, RequestReviewDto requestReviewDto) {
        Optional<FairReview> fairReview = Optional.ofNullable(fairReviewRepository.getFairReview(fairId, userId));
        fairReview.ifPresent(review -> review.updateFairReview(requestReviewDto.getContent(), requestReviewDto.getScore()));
        return fairReview;
    }

    public Optional<FairReview> deleteFairReview(Long userId, Long fairId) {
        Optional<FairReview> fairReview = Optional.ofNullable(fairReviewRepository.getFairReview(fairId, userId));
        fairReview.ifPresent(fairReviewRepository::delete);
        return fairReview;
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
