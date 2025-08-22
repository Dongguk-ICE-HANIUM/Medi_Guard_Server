package hanium.dongguk.drug.patientdrug.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TakingTypeErrorCode implements ErrorCode {
    NOT_FOUND_TAKING_TYPE(HttpStatus.NOT_FOUND, "TAKING_TYPE_001", "존재하지 않는 복용 방법입니다."),
    INVALID_TAKING_TYPE(HttpStatus.BAD_REQUEST, "TAKING_TYPE_002", "유효하지 않은 복용 방법입니다."),
    INVALID_SPECIFIC_DATE(HttpStatus.BAD_REQUEST, "TAKING_TYPE_003", "유효하지 않은 특정일입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
