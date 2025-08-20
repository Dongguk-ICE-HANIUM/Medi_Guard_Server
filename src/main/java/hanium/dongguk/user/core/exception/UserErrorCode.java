package hanium.dongguk.user.core.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER_001", "존재하지 않는 사용자입니다."),
    DEACTIVATED_USER(HttpStatus.BAD_REQUEST, "USER_002", "비활성화된 사용자입니다."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "USER_003", "이메일 형식이 올바르지 않습니다."),
    INVALID_BIRTHDAY(HttpStatus.BAD_REQUEST, "USER_004", "유효하지 않은 생년월일입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "USER_005", "이미 존재하는 이메일입니다."),
    INVALID_DUE_DATE_PAST(HttpStatus.BAD_REQUEST, "USER_006", "출산 예정일은 과거가 될 수 없습니다."),

    // == oauth 관련 오류 == //
    NOT_FOUND_GOOGLE_USER(HttpStatus.NOT_FOUND, "USER_007", "구글 사용자 정보를 찾을 수 없습니다."),
    GOOGLE_OAUTH_FAILED(HttpStatus.BAD_REQUEST, "USER_008", "구글 사용자 정보 조회 실패"),
    ALREADY_COMPLETED_SIGNUP(HttpStatus.BAD_REQUEST, "USER_009", "이미 회원가입이 완료된 사용자입니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
