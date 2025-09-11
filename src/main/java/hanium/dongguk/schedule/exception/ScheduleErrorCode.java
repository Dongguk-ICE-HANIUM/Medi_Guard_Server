package hanium.dongguk.schedule.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ScheduleErrorCode implements ErrorCode {

    INVALID_SCHEDULE_TIME(HttpStatus.BAD_REQUEST, "SCHEDULE_001", "진료 예정일은 과거가 될 수 없습니다."),
    DUPLICATE_SCHEDULE_TIME(HttpStatus.BAD_REQUEST, "SCHEDULE_002", "이전에 등록했던 진료일과 중복됩니다.");


    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
