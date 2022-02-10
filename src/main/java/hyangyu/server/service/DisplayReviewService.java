package hyangyu.server.service;

import hyangyu.server.domain.Display;
import hyangyu.server.domain.DisplayReview;
import hyangyu.server.domain.DisplayWarn;
import hyangyu.server.domain.User;
import hyangyu.server.dto.RequestReviewDto;
import hyangyu.server.dto.ReviewDto;
import hyangyu.server.repository.DisplayRepository;
import hyangyu.server.repository.DisplayReviewRepository;
import hyangyu.server.repository.DisplayWarnRepository;
import hyangyu.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DisplayReviewService {

    private final DisplayReviewRepository displayReviewRepository;
    private final DisplayWarnRepository displayWarnRepository;
    private final UserRepository userRepository;
    private final DisplayRepository displayRepository;

    public int saveDisplayReview(Long userId, Long displayId, RequestReviewDto requestReviewDto) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Display> display = displayRepository.findOne(displayId);
        int count = displayReviewRepository.getCount(display.get().getDisplayId(), user.get().getUserId());
        if (count == 0) {
            DisplayReview displayReview = DisplayReview.createDisplayReview(user.get(), display.get(), LocalDateTime.now(), requestReviewDto.getContent(), requestReviewDto.getScore(), 0);
            DisplayReview savedDisplayReview = displayReviewRepository.save(displayReview);
        }
        return count;
    }

    public Optional<DisplayReview> modifyDisplayReview(Long userId, Long displayId, RequestReviewDto requestReviewDto) {
        Optional<DisplayReview> displayReview = Optional.ofNullable(displayReviewRepository.getDisplayReview(displayId, userId));
        displayReview.ifPresent(review -> review.updateDisplayReview(requestReviewDto.getContent(), requestReviewDto.getScore()));
        return displayReview;
    }

    public Optional<DisplayReview> deleteDisplayReview(Long userId, Long displayId) {
        Optional<DisplayReview> displayReview = Optional.ofNullable(displayReviewRepository.getDisplayReview(displayId, userId));
        displayReview.ifPresent(displayReviewRepository::delete);
        return displayReview;
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

    public List<ReviewDto> getMyDisplayReviews(Long userId) {
        return displayReviewRepository.getMyDisplayReviews(userId);
    }
}
