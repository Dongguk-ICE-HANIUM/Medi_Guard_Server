package hanium.dongguk.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CalendarErrorCode implements ErrorCode {

    // 캘린더가 특정 날짜에 없음
    CALENDAR_NOT_FOUND(HttpStatus.NOT_FOUND, "CALENDAR_001", "해당 날짜의 캘린더 기록이 없습니다."),

    // 잘못된 날짜 형식
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "CALENDAR_002", "잘못된 날짜 형식입니다."),

    // 캘린더가 이미 존재 (중복 저장 방지용)
    CALENDAR_ALREADY_EXISTS(HttpStatus.CONFLICT, "CALENDAR_003", "해당 날짜에 이미 캘린더 기록이 존재합니다."),

    // 권한 없음 (다른 환자 캘린더 접근 시)
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "CALENDAR_004", "본인의 캘린더만 접근할 수 있습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}