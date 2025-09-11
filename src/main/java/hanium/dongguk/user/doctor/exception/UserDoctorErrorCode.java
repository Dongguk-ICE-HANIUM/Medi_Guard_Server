package hanium.dongguk.user.doctor.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserDoctorErrorCode implements ErrorCode {

    NOT_FOUND_USER_DOCTOR(HttpStatus.NOT_FOUND, "DOCTOR_001", "해당 의사를 찾을 수 없습니다.")
    ;


    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
