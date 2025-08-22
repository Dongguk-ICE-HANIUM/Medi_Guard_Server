package hanium.dongguk.calendar.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CalendarErrorCode implements ErrorCode {

    CALENDAR_NOT_FOUND(HttpStatus.NOT_FOUND, "CALENDAR_001", "해당 날짜의 캘린더 기록이 없습니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "CALENDAR_002", "잘못된 날짜 형식입니다."),
    CALENDAR_ALREADY_EXISTS(HttpStatus.CONFLICT, "CALENDAR_003", "해당 날짜에 이미 캘린더 기록이 존재합니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}