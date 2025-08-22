package hanium.dongguk.drug.patientdrug.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PatientDrugErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "PATIENT_DRUG_001", "존재하지 않는 환자 약물입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
