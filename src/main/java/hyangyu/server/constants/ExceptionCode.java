package hyangyu.server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    /* 400 - 잘못된 요청 */
    REVIEW_LENGTH_EXCESS(NOT_FOUND, "리뷰 길이가 300자를 초과합니다."),

    /* 401 - 인증 실패 */
    // token 관련,
    WRONG_TYPE_TOKEN(UNAUTHORIZED, "잘못된 JWT 서명을 가진 토큰입니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    WRONG_TOKEN(UNAUTHORIZED, "JWT 토큰이 잘못되었습니다."),
    UNKNOWN_ERROR(UNAUTHORIZED, "알 수 없는 요청 인증 에러! 헤더에 토큰을 넣어 보냈는지 다시 한 번 확인해보세요."),
    ACCESS_DENIED(UNAUTHORIZED, "접근이 거절되었습니다.")

    /* 404 - 찾을 수 없는 리소스 */,
    USER_NOT_FOUND(NOT_FOUND, "유효하지 않은 사용자입니다."),
    DISPLAY_NOT_FOUND(NOT_FOUND, "잘못된 전시 id입니다."),
    FAIR_NOT_FOUND(NOT_FOUND, "잘못된 박람회 id입니다."),
    FESTIVAL_NOT_FOUND(NOT_FOUND, "잘못된 페스티벌 id입니다."),
    REVIEW_NOT_FOUND(NOT_FOUND, "작성한 리뷰가 없습니다."),


    /* 409 - 중복된 리소스 */
    ALREADY_SAVED_REVIEW(CONFLICT, "이미 리뷰를 작성했습니다."),
    ALREADY_WARN_REVIEW(CONFLICT, "이미 신고한 리뷰입니다.");

    private final HttpStatus status;
    private final String message;
}
