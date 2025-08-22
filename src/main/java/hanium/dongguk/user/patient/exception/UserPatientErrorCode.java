package hanium.dongguk.user.patient.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserPatientErrorCode implements ErrorCode {
    NOT_FOUND_USER_PATIENT(HttpStatus.NOT_FOUND, "USER_PATIENT_001", "존재하지 않는 환자입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
