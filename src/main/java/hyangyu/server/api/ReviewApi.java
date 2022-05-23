package hyangyu.server.api;

import hyangyu.server.dto.review.*;
import hyangyu.server.exception.CustomException;
import hyangyu.server.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static hyangyu.server.constants.ExceptionCode.EVENT_NOT_FOUND;
import static hyangyu.server.constants.SuccessCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewApi {

    private final DisplayReviewService displayReviewService;
    private final FairReviewService fairReviewService;
    private final FestivalReviewService festivalReviewService;

    @PostMapping("/review/display/{displayId}")
    public ResponseEntity<ReviewResponse> saveDisplayReview(@PathVariable Long displayId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        displayReviewService.saveDisplayReview(requestReviewDto, displayId);

        return ReviewResponse.newResponse(REVIEW_SAVE_SUCCESS);
    }

    @PostMapping("/review/fair/{fairId}")
    public ResponseEntity<ReviewResponse> saveFairReview(@PathVariable Long fairId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        fairReviewService.saveFairReview(requestReviewDto, fairId);

        return ReviewResponse.newResponse(REVIEW_SAVE_SUCCESS);
    }

    @PostMapping("/review/festival/{festivalId}")
    public ResponseEntity<ReviewResponse> saveFestivalReview(@PathVariable Long festivalId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        festivalReviewService.saveFestivalReview(requestReviewDto, festivalId);

        return ReviewResponse.newResponse(REVIEW_SAVE_SUCCESS);
    }

    @PostMapping("/review/change/display/{displayId}")
    public ResponseEntity<ReviewResponse> updateDisplayReview(@PathVariable Long displayId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        displayReviewService.modifyDisplayReview(requestReviewDto, displayId);

        return ReviewResponse.newResponse(REVIEW_UPDATE_SUCCESS);
    }

    @PostMapping("/review/change/fair/{fairId}")
    public ResponseEntity<ReviewResponse> updateFairReview(@PathVariable Long fairId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        fairReviewService.modifyFairReview(requestReviewDto, fairId);

        return ReviewResponse.newResponse(REVIEW_UPDATE_SUCCESS);
    }

    @PostMapping("/review/change/festival/{festivalId}")
    public ResponseEntity<ReviewResponse> updateFestivalReview(@PathVariable Long festivalId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        festivalReviewService.modifyFestivalReview(requestReviewDto, festivalId);

        return ReviewResponse.newResponse(REVIEW_UPDATE_SUCCESS);
    }

    @DeleteMapping("/review/display/{displayId}")
    public ResponseEntity<ReviewResponse> deleteDisplayReview(@PathVariable Long displayId) throws Exception {
        displayReviewService.deleteDisplayReview(displayId);

        return ReviewResponse.newResponse(REVIEW_DELETE_SUCCESS);
    }

    @DeleteMapping("/review/fair/{fairId}")
    public ResponseEntity<ReviewResponse> deleteFairReview(@PathVariable Long fairId) throws Exception {
        fairReviewService.deleteFairReview(fairId);

        return ReviewResponse.newResponse(REVIEW_DELETE_SUCCESS);
    }

    @DeleteMapping("/review/festival/{festivalId}")
    public ResponseEntity<ReviewResponse> deleteFestivalReview(@PathVariable Long festivalId) throws Exception {
        festivalReviewService.deleteFestivalReview(festivalId);

        return ReviewResponse.newResponse(REVIEW_DELETE_SUCCESS);
    }

    @PostMapping("/review/accuse/{event}/{reviewId}")
    public ResponseEntity<ReviewResponse> accuseReview(@PathVariable Long reviewId, @PathVariable String event) throws Exception {
        if (event.equals("display")) {
            displayReviewService.accuseDisplayReview(reviewId);

            return ReviewResponse.newResponse(REVIEW_WARN_SUCCESS);
        } else if (event.equals("fair")) {
            fairReviewService.accuseFairReview(reviewId);

            return ReviewResponse.newResponse(REVIEW_WARN_SUCCESS);
        } else if (event.equals("festival")) {
            festivalReviewService.accuseFestivalReview(reviewId);

            return ReviewResponse.newResponse(REVIEW_WARN_SUCCESS);
        } else {
            throw new CustomException(EVENT_NOT_FOUND);
        }
    }

    @GetMapping("/show/review/display/{displayId}")
    public ResponseEntity<ReviewListResponse> getDisplayReviews(@PathVariable Long displayId) throws Exception {
        ReviewListResponseDto response = displayReviewService.getDisplayReviews(displayId);

        return ReviewListResponse.newResponse(REVIEW_READ_SUCCESS, response);
    }

    @GetMapping("/show/review/fair/{fairId}")
    public ResponseEntity<ReviewListResponse> getFairReviews(@PathVariable Long fairId) throws Exception {
        ReviewListResponseDto response = fairReviewService.getFairReviews(fairId);

        return ReviewListResponse.newResponse(REVIEW_READ_SUCCESS, response);
    }

    @GetMapping("/show/review/festival/{festivalId}")
    public ResponseEntity<ReviewListResponse> getFestivalReviews(@PathVariable Long festivalId) throws Exception {
        ReviewListResponseDto response = festivalReviewService.getFestivalReviews(festivalId);

        return ReviewListResponse.newResponse(REVIEW_READ_SUCCESS, response);
    }

    @GetMapping("/myreview")
    public ResponseEntity<MyReviewResponse> getMyReviews() {
        MyReviewResponseDto response = displayReviewService.getMyReviews();

        return MyReviewResponse.newResponse(REVIEW_READ_SUCCESS, response);
    }
}
