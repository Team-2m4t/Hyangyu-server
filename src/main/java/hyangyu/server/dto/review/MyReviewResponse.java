package hyangyu.server.dto.review;

import hyangyu.server.constants.SuccessCode;
import hyangyu.server.dto.BaseResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class MyReviewResponse extends BaseResponse {
    MyReviewResponseDto data;

    private MyReviewResponse(Boolean success, String msg, MyReviewResponseDto data) {
        super(success, msg);
        this.data = data;
    }

    public static MyReviewResponse of(Boolean success, String message, MyReviewResponseDto data) {
        return new MyReviewResponse(success, message, data);
    }

    public static ResponseEntity<MyReviewResponse> newResponse(SuccessCode code, MyReviewResponseDto data) {
        MyReviewResponse response = MyReviewResponse.of(true, code.getMessage(), data);
        return new ResponseEntity<MyReviewResponse>(response, code.getStatus());
    }
}
