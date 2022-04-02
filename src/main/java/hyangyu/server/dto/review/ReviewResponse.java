package hyangyu.server.dto.review;

import hyangyu.server.constants.SuccessCode;
import hyangyu.server.dto.BaseResponse;
import org.springframework.http.ResponseEntity;

public class ReviewResponse extends BaseResponse {

    private ReviewResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static ReviewResponse of(Boolean success, String msg) {
        return new ReviewResponse(success, msg);
    }

    public static ResponseEntity<ReviewResponse> newResponse(SuccessCode code) {
        return new ResponseEntity(ReviewResponse.of(true, code.getMessage()), code.getStatus());
    }
}
