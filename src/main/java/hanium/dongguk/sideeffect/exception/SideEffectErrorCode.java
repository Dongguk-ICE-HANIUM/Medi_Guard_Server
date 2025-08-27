package hanium.dongguk.sideeffect.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SideEffectErrorCode implements ErrorCode {

    SIDE_EFFECT_NOT_FOUND(HttpStatus.NOT_FOUND, "SIDE_EFFECT_001", "부작용 기록을 찾을 수 없습니다."),
    EMPTY_DESCRIPTION(HttpStatus.BAD_REQUEST, "SIDE_EFFECT_002", "부작용 설명을 입력해주세요."),
    CALENDAR_DRUG_NOT_FOUND(HttpStatus.NOT_FOUND, "SIDE_EFFECT_003", "해당 캘린더 약물 기록이 존재하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "SIDE_EFFECT_004", "본인의 부작용 기록만 접근할 수 있습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}