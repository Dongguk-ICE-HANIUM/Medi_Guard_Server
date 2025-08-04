package hanium.dongguk.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SideEffectErrorCode implements ErrorCode {

    // 부작용 기록이 존재하지 않을 때
    SIDE_EFFECT_NOT_FOUND(HttpStatus.NOT_FOUND, "SIDE_EFFECT_001", "부작용 기록을 찾을 수 없습니다."),

    // 부작용 설명이 비었을 때
    EMPTY_DESCRIPTION(HttpStatus.BAD_REQUEST, "SIDE_EFFECT_002", "부작용 설명을 입력해주세요."),

    // 해당 캘린더 약물(CalendarDrug)이 없을 때
    CALENDAR_DRUG_NOT_FOUND(HttpStatus.NOT_FOUND, "SIDE_EFFECT_003", "해당 캘린더 약물 기록이 존재하지 않습니다."),

    // 권한 없는 사용자가 접근할 때
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "SIDE_EFFECT_004", "본인의 부작용 기록만 접근할 수 있습니다.");

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