package hyangyu.server.service;

import hyangyu.server.domain.Display;
import hyangyu.server.domain.DisplayReview;
import hyangyu.server.domain.DisplayWarn;
import hyangyu.server.domain.User;
import hyangyu.server.dto.review.MyReviewResponseDto;
import hyangyu.server.dto.review.RequestReviewDto;
import hyangyu.server.dto.review.ReviewDto;
import hyangyu.server.dto.review.ReviewListResponseDto;
import hyangyu.server.exception.CustomException;
import hyangyu.server.jwt.SecurityUtil;
import hyangyu.server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hyangyu.server.constants.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DisplayReviewService {

    private final DisplayReviewRepository displayReviewRepository;
    private final FairReviewRepository fairReviewRepository;
    private final FestivalReviewRepository festivalReviewRepository;
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

    public void accuseDisplayReview(Long reviewId) {
        User user = getUser();

        DisplayReview displayReview = displayReviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        if (user.equals(displayReview.getUser())) {
            throw new CustomException(ACCUSE_DENIED);
        }

        Optional<DisplayWarn> displayWarn = displayWarnRepository.getDisplayWarn(reviewId, user.getUserId());
        if (displayWarn.isEmpty()) {
            DisplayWarn warn = DisplayWarn.createDisplayWarn(displayReview, user);
            displayWarnRepository.save(warn);

            displayReview.increaseWarn();
            if (displayReview.getWarn() == 3) {
                displayWarnRepository.deleteDisplayWarns(displayReview.getReviewId());
                displayReviewRepository.delete(displayReview);
            }
        }
        if (displayWarn.isPresent()) {
            throw new CustomException(ALREADY_WARN_REVIEW);
        }
    }

    public ReviewListResponseDto getDisplayReviews(Long displayId) {
        User user = getUser();

        Display display = getDisplay(displayId);

        List<ReviewDto> reviews = displayReviewRepository.findAllByDisplayOrderByCreateTimeDesc(display).stream()
                .map(review -> ReviewDto.of(review.getReviewId(), review.getUser().getImage(), review.getUser().getUsername(), review.getCreateTime(), review.getContent(), review.getScore()))
                .collect(Collectors.toList());

        return ReviewListResponseDto.of(reviews);
    }


    public MyReviewResponseDto getMyReviews() {
        User user = getUser();

        List<ReviewDto> displayReviews = displayReviewRepository.findAllByUserOrderByReviewIdDesc(user).stream()
                .map(review -> ReviewDto.of(review.getReviewId(), review.getUser().getImage(), review.getUser().getUsername(), review.getCreateTime(), review.getContent(), review.getScore()))
                .collect(Collectors.toList());
        List<ReviewDto> fairReviews = fairReviewRepository.findAllByUserOrderByReviewIdDesc(user).stream()
                .map(review -> ReviewDto.of(review.getReviewId(), review.getUser().getImage(), review.getUser().getUsername(), review.getCreateTime(), review.getContent(), review.getScore()))
                .collect(Collectors.toList());
        List<ReviewDto> festivalReviews = festivalReviewRepository.findAllByUserOrderByReviewIdDesc(user).stream()
                .map(review -> ReviewDto.of(review.getReviewId(), review.getUser().getImage(), review.getUser().getUsername(), review.getCreateTime(), review.getContent(), review.getScore()))
                .collect(Collectors.toList());

        return MyReviewResponseDto.of(displayReviews, fairReviews, festivalReviews);
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
