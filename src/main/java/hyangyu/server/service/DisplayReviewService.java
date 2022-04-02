package hyangyu.server.service;

import hyangyu.server.domain.Display;
import hyangyu.server.domain.DisplayReview;
import hyangyu.server.domain.DisplayWarn;
import hyangyu.server.domain.User;
import hyangyu.server.dto.review.MyReviewDto;
import hyangyu.server.dto.review.RequestReviewDto;
import hyangyu.server.dto.review.ReviewDto;
import hyangyu.server.exception.CustomException;
import hyangyu.server.jwt.SecurityUtil;
import hyangyu.server.repository.DisplayRepository;
import hyangyu.server.repository.DisplayReviewRepository;
import hyangyu.server.repository.DisplayWarnRepository;
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
public class DisplayReviewService {

    private final DisplayReviewRepository displayReviewRepository;
    private final DisplayWarnRepository displayWarnRepository;
    private final UserRepository userRepository;
    private final DisplayRepository displayRepository;

    public void saveDisplayReview(RequestReviewDto requestReviewDto, Long displayId) {
        User user = getUser();

        Display display = getDisplay(displayId);

        // 길이 확인
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            throw new CustomException(REVIEW_LENGTH_EXCESS);
        }

        //이미 전시에 대한 리뷰 달았는지 확인 후 저장
        int count = displayReviewRepository.getCount(display.getDisplayId(), user.getUserId());
        if (count == 0) {
            DisplayReview displayReview = DisplayReview.createDisplayReview(user, display, LocalDateTime.now(), requestReviewDto.getContent(), requestReviewDto.getScore(), 0);
            displayReviewRepository.save(displayReview);
        } else {
            throw new CustomException(ALREADY_SAVED_REVIEW);
        }
    }

    public void modifyDisplayReview(RequestReviewDto requestReviewDto, Long displayId) {
        User user = getUser();

        Display display = getDisplay(displayId);

        // 길이 확인
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            throw new CustomException(REVIEW_LENGTH_EXCESS);
        }

        Optional<DisplayReview> displayReview = Optional.ofNullable(displayReviewRepository.getDisplayReview(display.getDisplayId(), user.getUserId()));
        if (displayReview.isEmpty()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
        displayReview.ifPresent(review -> review.updateDisplayReview(requestReviewDto.getContent(), requestReviewDto.getScore()));
    }

    public void deleteDisplayReview(Long displayId) {
        User user = getUser();

        Display display = getDisplay(displayId);

        Optional<DisplayReview> displayReview = Optional.ofNullable(displayReviewRepository.getDisplayReview(display.getDisplayId(), user.getUserId()));
        if (displayReview.isEmpty()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
        displayReview.ifPresent(displayReviewRepository::delete);
    }

    public Optional<DisplayReview> findReview(Long reviewId) {
        return displayReviewRepository.findById(reviewId);
    }

    public String accuseDisplayReview(DisplayReview displayReview, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (userId.equals(displayReview.getUser().getUserId())) {
            return "내가 쓴 리뷰는 신고할 수 없습니다.";
        }

        Optional<DisplayWarn> displayWarn = Optional.ofNullable(displayWarnRepository.getDisplayWarn(displayReview.getReviewId(), userId));
        if (displayWarn.isEmpty() && user.isPresent()) {
            DisplayWarn warn = DisplayWarn.createDisplayWarn(displayReview, user.get());
            displayWarnRepository.save(warn);

            displayReview.increaseWarn();
            if (displayReview.getWarn() == 3) {
                displayWarnRepository.deleteDisplayWarns(displayReview.getReviewId());
                displayReviewRepository.delete(displayReview);
                return "신고 3번이 누적되어 자동 삭제되었습니다.";
            }
        } else if (displayWarn.isPresent()) {
            return "이미 신고한 리뷰입니다.";
        }

        return "신고가 완료되었습니다.";
    }

    public List<ReviewDto> getDisplayReviews(Long displayId) {
        return displayReviewRepository.getDisplayReviews(displayId);
    }

    public List<MyReviewDto> getMyDisplayReviews(Long userId) {
        return displayReviewRepository.getMyDisplayReviews(userId);
    }

    private User getUser() {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private Display getDisplay(Long displayId) {
        return displayRepository.findOne(displayId)
                .orElseThrow(() -> new CustomException(DISPLAY_NOT_FOUND));
    }
}
