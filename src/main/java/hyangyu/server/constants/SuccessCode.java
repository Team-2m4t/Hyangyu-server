package hyangyu.server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    REVIEW_SAVE_SUCCESS(OK, "리뷰 작성에 성공했습니다."),
    REVIEW_UPDATE_SUCCESS(OK, "리뷰 수정에 성공했습니다."),
    REVIEW_DELETE_SUCCESS(OK, "리뷰 삭제에 성공했습니다."),
    REVIEW_WARN_SUCCESS(OK, "리뷰 신고에 성공했습니다.");

    private final HttpStatus status;
    private final String message;
}
