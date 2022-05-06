package hyangyu.server.api;

import hyangyu.server.domain.*;
import hyangyu.server.dto.*;
import hyangyu.server.dto.review.*;
import hyangyu.server.dto.user.UserDto;
import hyangyu.server.exception.CustomException;
import hyangyu.server.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static hyangyu.server.constants.ExceptionCode.EVENT_NOT_FOUND;
import static hyangyu.server.constants.SuccessCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewApi {

    private final DisplayReviewService displayReviewService;
    private final FairReviewService fairReviewService;
    private final FestivalReviewService festivalReviewService;
    private final DisplayService displayService;
    private final FairService fairService;
    private final FestivalService festivalService;
    private final UserService userService;

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
    public ResponseEntity getDisplayReviews(@PathVariable Long displayId) throws Exception {
        //전시 검색
        Optional<Display> display = displayService.findOne(displayId);
        if (display.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 전시 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        List<ReviewDto> displayReviews = displayReviewService.getDisplayReviews(displayId);
        ShowReviewsDto showReviewsDto = new ShowReviewsDto(displayReviews);
        ReviewsResponseDto reviewResponseDto = new ReviewsResponseDto(200, showReviewsDto);
        return new ResponseEntity(reviewResponseDto, HttpStatus.OK);
    }

    @GetMapping("/show/review/fair/{fairId}")
    public ResponseEntity getFairReviews(@PathVariable Long fairId) throws Exception {
        //전시 검색
        Optional<Fair> fair = fairService.findOne(fairId);
        if (fair.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 박람회 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        List<ReviewDto> fairReviews = fairReviewService.getFairReviews(fairId);
        ShowReviewsDto showReviewsDto = new ShowReviewsDto(fairReviews);
        ReviewsResponseDto reviewResponseDto = new ReviewsResponseDto(200, showReviewsDto);
        return new ResponseEntity(reviewResponseDto, HttpStatus.OK);
    }

    @GetMapping("/show/review/festival/{festivalId}")
    public ResponseEntity getFestivalReviews(@PathVariable Long festivalId) throws Exception {
        //전시 검색
        Optional<Festival> festival = festivalService.findOne(festivalId);
        if (festival.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 페스티벌 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        List<ReviewDto> festivalReviews = festivalReviewService.getFestivalReviews(festivalId);
        ShowReviewsDto showReviewsDto = new ShowReviewsDto(festivalReviews);
        ReviewsResponseDto reviewResponseDto = new ReviewsResponseDto(200, showReviewsDto);
        return new ResponseEntity(reviewResponseDto, HttpStatus.OK);
    }

    @GetMapping("/myreview")
    public ResponseEntity getMyReviews() throws Exception {
        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        List<MyReviewDto> myDisplayReviews = displayReviewService.getMyDisplayReviews(user.getUserId());
        List<MyReviewDto> myFairReviews = fairReviewService.getMyFairReviews(user.getUserId());
        List<MyReviewDto> myFestivalReviews = festivalReviewService.getMyFestivalReviews(user.getUserId());

        MyReviewsDto myReviewsDto = new MyReviewsDto(myDisplayReviews, myFairReviews, myFestivalReviews);
        MyReviewsResponseDto myReviewsResponseDto = new MyReviewsResponseDto(200, myReviewsDto);
        return new ResponseEntity(myReviewsResponseDto, HttpStatus.OK);
    }
}
