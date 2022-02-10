package hyangyu.server.service;

import hyangyu.server.domain.*;
import hyangyu.server.dto.review.RequestReviewDto;
import hyangyu.server.dto.review.ReviewDto;
import hyangyu.server.repository.FestivalRepository;
import hyangyu.server.repository.FestivalReviewRepository;
import hyangyu.server.repository.FestivalWarnRepository;
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
public class FestivalReviewService {

    private final FestivalReviewRepository festivalReviewRepository;
    private final FestivalWarnRepository festivalWarnRepository;
    private final UserRepository userRepository;
    private final FestivalRepository festivalRepository;

    public int saveFestivalReview(Long userId, Long festivalId, RequestReviewDto requestReviewDto) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Festival> festival = festivalRepository.findOne(festivalId);
        int count = festivalReviewRepository.getCount(festival.get().getFestivalId(), user.get().getUserId());
        if (count == 0) {
            FestivalReview festivalReview = FestivalReview.createFestivalReview(user.get(), festival.get(), LocalDateTime.now(), requestReviewDto.getContent(), requestReviewDto.getScore(), 0);
            FestivalReview savedFestivalReview = festivalReviewRepository.save(festivalReview);
        }
        return count;
    }

    public Optional<FestivalReview> modifyFestivalReview(Long userId, Long festivalId, RequestReviewDto requestReviewDto) {
        Optional<FestivalReview> festivalReview = Optional.ofNullable(festivalReviewRepository.getFestivalReview(festivalId, userId));
        festivalReview.ifPresent(review -> review.updateFestivalReview(requestReviewDto.getContent(), requestReviewDto.getScore()));
        return festivalReview;
    }

    public Optional<FestivalReview> deleteFestivalReview(Long userId, Long festivalId) {
        Optional<FestivalReview> festivalReview = Optional.ofNullable(festivalReviewRepository.getFestivalReview(festivalId, userId));
        festivalReview.ifPresent(festivalReviewRepository::delete);
        return festivalReview;
    }

    public Optional<FestivalReview> findReview(Long reviewId) {
        return festivalReviewRepository.findById(reviewId);
    }

    public String accuseFestivalReview(FestivalReview festivalReview, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (userId.equals(festivalReview.getUser().getUserId())) {
            return "내가 쓴 리뷰는 신고할 수 없습니다.";
        }

        Optional<FestivalWarn> festivalWarn = Optional.ofNullable(festivalWarnRepository.getFestivalWarn(festivalReview.getReviewId(), userId));
        if (festivalWarn.isEmpty() && user.isPresent()) {
            FestivalWarn warn = FestivalWarn.createFestivalWarn(festivalReview, user.get());
            festivalWarnRepository.save(warn);

            festivalReview.increaseWarn();
            if (festivalReview.getWarn() == 3) {
                festivalWarnRepository.deleteFestivalWarns(festivalReview.getReviewId());
                festivalReviewRepository.delete(festivalReview);
                return "신고 3번이 누적되어 자동 삭제되었습니다.";
            }
        } else if (festivalWarn.isPresent()) {
            return "이미 신고한 리뷰입니다.";
        }

        return "신고가 완료되었습니다.";
    }

    public List<ReviewDto> getMyFestivalReviews(Long userId) {
        return festivalReviewRepository.getMyFestivalReviews(userId);
    }
}
