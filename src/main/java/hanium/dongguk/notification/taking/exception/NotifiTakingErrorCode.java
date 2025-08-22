package hanium.dongguk.notification.taking.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotifiTakingErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFI_TAKING_001", "존재하지 않는 복약 알림입니다."),
    DUPLICATED_TIME(HttpStatus.BAD_REQUEST, "NOTIFI_TAKING_002", "복약 알림 시간이 이미 존재합니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
