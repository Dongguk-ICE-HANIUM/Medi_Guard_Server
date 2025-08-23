package hanium.dongguk.auth.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCode {

    CANNOT_JSON_PROCESS(HttpStatus.BAD_REQUEST, "AUTH_001", "JSON을 직렬화 혹은 역직렬화 할 수 없습니다."),
    APPLE_TOKEN_REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "AUTH_002", "apple token 요청에 실패했습니다."),
    NOT_MATCHED_PUBLIC_KEY(HttpStatus.UNAUTHORIZED, "AUTH_003", "적절한 공개키를 찾을 수 없습니다."),
    APPLE_PUBLIC_KEY_REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "AUTH_004", "public key 요청에 실패했습니다."),
    NOT_FOUND_ALGORITHM(HttpStatus.UNPROCESSABLE_ENTITY, "AUTH_005", "지원하지 않는 공개키 알고리즘입니다."),
    NOT_SUPPORTED_ALGORITHM(HttpStatus.UNPROCESSABLE_ENTITY, "AUTH_006", "Spring Security에서 지원하지 않는 공개키 알고리즘입니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "AUTH_007", "이메일 형식이 올바르지 않습니다."),
    INVALID_BIRTHDAY(HttpStatus.BAD_REQUEST, "AUTH_008", "유효하지 않은 생년월일입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH_009", "이미 존재하는 이메일입니다."),
    INVALID_DUE_DATE_PAST(HttpStatus.BAD_REQUEST, "AUTH_010", "출산 예정일은 과거가 될 수 없습니다."),

    // == oauth 관련 오류 == //
    NOT_FOUND_GOOGLE_USER(HttpStatus.NOT_FOUND, "AUTH_011", "구글 사용자 정보를 찾을 수 없습니다."),
    GOOGLE_OAUTH_FAILED(HttpStatus.BAD_REQUEST, "AUTH_012", "구글 사용자 정보 조회 실패"),
    ALREADY_COMPLETED_SIGNUP(HttpStatus.BAD_REQUEST, "AUTH_013", "이미 회원가입이 완료된 사용자입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
