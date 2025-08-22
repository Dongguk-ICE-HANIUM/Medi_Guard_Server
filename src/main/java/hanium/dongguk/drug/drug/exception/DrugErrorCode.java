package hanium.dongguk.drug.drug.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DrugErrorCode implements ErrorCode {
    NOT_FOUND_DRUG(HttpStatus.NOT_FOUND, "DRUG_001", "존재하지 않는 약물입니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
