package hanium.dongguk.notification.taking.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotifiTakingErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "NOTIFI_TAKING_001", "존재하지 않는 복약 알림입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
