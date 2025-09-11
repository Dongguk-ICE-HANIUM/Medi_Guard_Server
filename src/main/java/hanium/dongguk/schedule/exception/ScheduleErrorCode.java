package hanium.dongguk.schedule.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ScheduleErrorCode implements ErrorCode {

    INVALID_SCHEDULE_TIME(HttpStatus.BAD_REQUEST, "SCHEDULE_001", "진료 예정일은 과거가 될 수 없습니다."),
    DUPLICATE_SCHEDULE_TIME(HttpStatus.BAD_REQUEST, "SCHEDULE_002", "이전에 등록했던 진료일과 중복됩니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "SCHEDULE_003", "등록된 진료 예정일을 찾을 수 없습니다."),
    NOT_WAITING_SCHEDULE(HttpStatus.BAD_REQUEST, "SCHEDULE_004", "대기중인 진료를 선택해야 합니다."),
    NOT_COMPLETED_SCHEDULE(HttpStatus.BAD_REQUEST, "SCHEDULE_005", "완료된 진료를 선택해야 합니다."),
    NOT_TODAY_SCHEDULE_TIME(HttpStatus.BAD_REQUEST, "SCHEDULE_006", "오늘의 일정의 진료만 시작가능합니다.")
    ;


    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
