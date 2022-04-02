package hyangyu.server.service;

import hyangyu.server.domain.*;
import hyangyu.server.dto.review.MyReviewDto;
import hyangyu.server.dto.review.RequestReviewDto;
import hyangyu.server.dto.review.ReviewDto;
import hyangyu.server.exception.CustomException;
import hyangyu.server.jwt.SecurityUtil;
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

import static hyangyu.server.constants.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalReviewService {

    private final FestivalReviewRepository festivalReviewRepository;
    private final FestivalWarnRepository festivalWarnRepository;
    private final UserRepository userRepository;
    private final FestivalRepository festivalRepository;

    public void saveFestivalReview(RequestReviewDto requestReviewDto, Long festivalId) {
        User user = getUser();

        Festival festival = getFestival(festivalId);

        // 길이 확인
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            throw new CustomException(REVIEW_LENGTH_EXCESS);
        }

        //이미 페스티벌에 대한 리뷰 달았는지 확인 후 저장
        int count = festivalReviewRepository.getCount(festival.getFestivalId(), user.getUserId());
        if (count == 0) {
            FestivalReview festivalReview = FestivalReview.createFestivalReview(user, festival, LocalDateTime.now(), requestReviewDto.getContent(), requestReviewDto.getScore(), 0);
            festivalReviewRepository.save(festivalReview);
        } else {
            throw new CustomException(ALREADY_SAVED_REVIEW);
        }
    }

    public void modifyFestivalReview(RequestReviewDto requestReviewDto, Long festivalId) {
        User user = getUser();

        Festival festival = getFestival(festivalId);

        // 길이 확인
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            throw new CustomException(REVIEW_LENGTH_EXCESS);
        }

        Optional<FestivalReview> festivalReview = Optional.ofNullable(festivalReviewRepository.getFestivalReview(festival.getFestivalId(), user.getUserId()));
        if (festivalReview.isEmpty()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
        festivalReview.ifPresent(review -> review.updateFestivalReview(requestReviewDto.getContent(), requestReviewDto.getScore()));
    }

    public void deleteFestivalReview(Long festivalId) {
        User user = getUser();

        Festival festival = getFestival(festivalId);

        Optional<FestivalReview> festivalReview = Optional.ofNullable(festivalReviewRepository.getFestivalReview(festival.getFestivalId(), user.getUserId()));
        if (festivalReview.isEmpty()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
        festivalReview.ifPresent(festivalReviewRepository::delete);
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

    public List<ReviewDto> getFestivalReviews(Long festivalId) {
        return festivalReviewRepository.getFestivalReviews(festivalId);
    }

    public List<MyReviewDto> getMyFestivalReviews(Long userId) {
        return festivalReviewRepository.getMyFestivalReviews(userId);
    }

    private User getUser() {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return user;
    }

    private Festival getFestival(Long festivalId) {
        Festival festival = festivalRepository.findOne(festivalId)
                .orElseThrow(() -> new CustomException(FESTIVAL_NOT_FOUND));
        return festival;
    }
}
