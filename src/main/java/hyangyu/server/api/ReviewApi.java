package hyangyu.server.api;

import hyangyu.server.domain.*;
import hyangyu.server.dto.*;
import hyangyu.server.dto.review.*;
import hyangyu.server.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity saveDisplayReview(@PathVariable Long displayId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //전시 검색
        Optional<Display> display = displayService.findOne(displayId);
        if (display.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 전시 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        //리뷰 저장
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            return new ResponseEntity(new ErrorDto(404, "리뷰 길이가 300자를 초과합니다."), HttpStatus.BAD_REQUEST);
        }

        int count = displayReviewService.saveDisplayReview(user.getUserId(), displayId, requestReviewDto);
        if (count == 0) {
            SaveReviewResponseDto saveReviewResponseDto = new SaveReviewResponseDto(200, "리뷰 작성이 완료되었습니다.");
            return new ResponseEntity(saveReviewResponseDto, httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity(new ErrorDto(404, "이미 전시에 대한 리뷰를 달았습니다."), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/review/fair/{fairId}")
    public ResponseEntity saveFairReview(@PathVariable Long fairId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //박람회 검색
        Optional<Fair> fair = fairService.findOne(fairId);
        if (fair.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 박람회 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        //리뷰 저장
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            return new ResponseEntity(new ErrorDto(404, "리뷰 길이가 300자를 초과합니다."), HttpStatus.BAD_REQUEST);
        }

        int count = fairReviewService.saveFairReview(user.getUserId(), fairId, requestReviewDto);
        if (count == 0) {
            SaveReviewResponseDto saveReviewResponseDto = new SaveReviewResponseDto(200, "리뷰 작성이 완료되었습니다.");
            return new ResponseEntity(saveReviewResponseDto, httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity(new ErrorDto(404, "이미 박람회에 대한 리뷰를 달았습니다."), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/review/festival/{festivalId}")
    public ResponseEntity saveFestivalReview(@PathVariable Long festivalId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //페스티벌 검색
        Optional<Festival> festival = festivalService.findOne(festivalId);
        if (festival.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 페스티벌 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        //리뷰 저장
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            return new ResponseEntity(new ErrorDto(404, "리뷰 길이가 300자를 초과합니다."), HttpStatus.BAD_REQUEST);
        }

        int count = festivalReviewService.saveFestivalReview(user.getUserId(), festivalId, requestReviewDto);
        if (count == 0) {
            SaveReviewResponseDto saveReviewResponseDto = new SaveReviewResponseDto(200, "리뷰 작성이 완료되었습니다.");
            return new ResponseEntity(saveReviewResponseDto, httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity(new ErrorDto(404, "이미 페스티벌에 대한 리뷰를 달았습니다."), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/review/change/display/{displayId}")
    public ResponseEntity updateDisplayReview(@PathVariable Long displayId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //전시 검색
        Optional<Display> display = displayService.findOne(displayId);
        if (display.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 전시 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        //리뷰 길이 제한
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            return new ResponseEntity(new ErrorDto(404, "리뷰 길이가 300자를 초과합니다."), HttpStatus.BAD_REQUEST);
        }
        Optional<DisplayReview> displayReview = displayReviewService.modifyDisplayReview(user.getUserId(), displayId, requestReviewDto);
        if (displayReview.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "수정할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
        } else {
            ResponseDto responseDto = new ResponseDto(200, "리뷰 수정이 완료되었습니다.");
            return new ResponseEntity(responseDto, httpHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/review/change/fair/{fairId}")
    public ResponseEntity updateFairReview(@PathVariable Long fairId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //박람회 검색
        Optional<Fair> fair = fairService.findOne(fairId);
        if (fair.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 박람회 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        //리뷰 길이 제한
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            return new ResponseEntity(new ErrorDto(404, "리뷰 길이가 300자를 초과합니다."), HttpStatus.BAD_REQUEST);
        }
        Optional<FairReview> fairReview = fairReviewService.modifyFairReview(user.getUserId(), fairId, requestReviewDto);
        if (fairReview.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "수정할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
        } else {
            ResponseDto responseDto = new ResponseDto(200, "리뷰 수정이 완료되었습니다.");
            return new ResponseEntity(responseDto, httpHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/review/change/festival/{festivalId}")
    public ResponseEntity updateFestivalReview(@PathVariable Long festivalId, @RequestBody RequestReviewDto requestReviewDto) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //페스티벌 검색
        Optional<Festival> festival = festivalService.findOne(festivalId);
        if (festival.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 페스티벌 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        //리뷰 길이 제한
        int length = requestReviewDto.getContent().length();
        if (length > 300) {
            return new ResponseEntity(new ErrorDto(404, "리뷰 길이가 300자를 초과합니다."), HttpStatus.BAD_REQUEST);
        }
        Optional<FestivalReview> festivalReview = festivalReviewService.modifyFestivalReview(user.getUserId(), festivalId, requestReviewDto);
        if (festivalReview.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "수정할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
        } else {
            ResponseDto responseDto = new ResponseDto(200, "리뷰 수정이 완료되었습니다.");
            return new ResponseEntity(responseDto, httpHeaders, HttpStatus.OK);
        }
    }

    @DeleteMapping("/review/display/{displayId}")
    public ResponseEntity deleteDisplayReview(@PathVariable Long displayId) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //전시 검색
        Optional<Display> display = displayService.findOne(displayId);
        if (display.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 전시 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        Optional<DisplayReview> displayReview = displayReviewService.deleteDisplayReview(user.getUserId(), displayId);
        if (displayReview.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "삭제할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
        } else {
            ResponseDto responseDto = new ResponseDto(200, "리뷰 삭제가 완료되었습니다.");
            return new ResponseEntity(responseDto, httpHeaders, HttpStatus.OK);
        }
    }

    @DeleteMapping("/review/fair/{fairId}")
    public ResponseEntity deleteFairReview(@PathVariable Long fairId) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //박람회 검색
        Optional<Fair> fair = fairService.findOne(fairId);
        if (fair.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 박람회 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        Optional<FairReview> fairReview = fairReviewService.deleteFairReview(user.getUserId(), fairId);
        if (fairReview.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "삭제할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
        } else {
            ResponseDto responseDto = new ResponseDto(200, "리뷰 삭제가 완료되었습니다.");
            return new ResponseEntity(responseDto, httpHeaders, HttpStatus.OK);
        }
    }

    @DeleteMapping("/review/festival/{festivalId}")
    public ResponseEntity deleteFestivalReview(@PathVariable Long festivalId) throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        //페스티벌 검색
        Optional<Festival> festival = festivalService.findOne(festivalId);
        if (festival.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "잘못된 페스티벌 번호입니다."), HttpStatus.BAD_REQUEST);
        }

        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        Optional<FestivalReview> festivalReview = festivalReviewService.deleteFestivalReview(user.getUserId(), festivalId);
        if (festivalReview.isEmpty()) {
            return new ResponseEntity(new ErrorDto(404, "삭제할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
        } else {
            ResponseDto responseDto = new ResponseDto(200, "리뷰 삭제가 완료되었습니다.");
            return new ResponseEntity(responseDto, httpHeaders, HttpStatus.OK);
        }
    }

    @PostMapping("/review/accuse/{event}/{reviewId}")
    public ResponseEntity accuseReview(@PathVariable Long reviewId, @PathVariable String event) throws Exception {
        //사용자 검색
        UserDto user = userService.getMyUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity(new ErrorDto(401, "유효하지 않은 사용자입니다."), HttpStatus.BAD_REQUEST);
        }

        if (event.equals("display")) {
            //리뷰 검색
            Optional<DisplayReview> displayReview = displayReviewService.findReview(reviewId);
            if (displayReview.isEmpty()) {
                return new ResponseEntity(new ErrorDto(404, "신고할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
            } else {
                String message = displayReviewService.accuseDisplayReview(displayReview.get(), user.getUserId());
                if (message.equals("내가 쓴 리뷰는 신고할 수 없습니다.")) {
                    return new ResponseEntity(new ErrorDto(404, message), HttpStatus.BAD_REQUEST);
                }
                if (message.equals("이미 신고한 리뷰입니다.")) {
                    return new ResponseEntity(new ErrorDto(404, message), HttpStatus.BAD_REQUEST);
                }
                ResponseDto responseDto = new ResponseDto(200, message);
                return new ResponseEntity(responseDto, HttpStatus.OK);
            }

        } else if (event.equals("fair")) {
            //리뷰 검색
            Optional<FairReview> fairReview = fairReviewService.findReview(reviewId);
            if (fairReview.isEmpty()) {
                return new ResponseEntity(new ErrorDto(404, "신고할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
            } else {
                String message = fairReviewService.accuseFairReview(fairReview.get(), user.getUserId());
                if (message.equals("내가 쓴 리뷰는 신고할 수 없습니다.")) {
                    return new ResponseEntity(new ErrorDto(404, message), HttpStatus.BAD_REQUEST);
                }
                if (message.equals("이미 신고한 리뷰입니다.")) {
                    return new ResponseEntity(new ErrorDto(404, message), HttpStatus.BAD_REQUEST);
                }
                ResponseDto responseDto = new ResponseDto(200, message);
                return new ResponseEntity(responseDto, HttpStatus.OK);
            }

        } else if (event.equals("festival")) {
            //리뷰 검색
            Optional<FestivalReview> festivalReview = festivalReviewService.findReview(reviewId);
            if (festivalReview.isEmpty()) {
                return new ResponseEntity(new ErrorDto(404, "신고할 리뷰가 없습니다."), HttpStatus.BAD_REQUEST);
            } else {
                String message = festivalReviewService.accuseFestivalReview(festivalReview.get(), user.getUserId());
                if (message.equals("내가 쓴 리뷰는 신고할 수 없습니다.")) {
                    return new ResponseEntity(new ErrorDto(404, message), HttpStatus.BAD_REQUEST);
                }
                if (message.equals("이미 신고한 리뷰입니다.")) {
                    return new ResponseEntity(new ErrorDto(404, message), HttpStatus.BAD_REQUEST);
                }
                ResponseDto responseDto = new ResponseDto(200, message);
                return new ResponseEntity(responseDto, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(new ErrorDto(404, "이벤트명이 잘못되었습니다."), HttpStatus.BAD_REQUEST);
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
