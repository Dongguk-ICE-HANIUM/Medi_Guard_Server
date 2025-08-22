package hanium.dongguk.auth.exception;

import hanium.dongguk.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCode {

    CANNOT_JSON_PROCESS(HttpStatus.BAD_REQUEST, "AUTH_001", "JSON을 직렬화 혹은 역직렬화 할 수 없습니다."),
    APPLE_TOKEN_REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "AUTH_002", "apple token 요청에 실패했습니다."),
    NOT_MATCHED_PUBLIC_KEY(HttpStatus.UNAUTHORIZED, "AUTH_003", "적절한 공개키를 찾을 수 없습니다."),
    APPLE_PUBLIC_KEY_REQUEST_FAILED(HttpStatus.BAD_GATEWAY, "AUTH_004", "public key 요청에 실패했습니다."),
    NOT_FOUND_ALGORITHM(HttpStatus.UNPROCESSABLE_ENTITY, "AUTH_005", "지원하지 않는 공개키 알고리즘입니다."),
    NOT_SUPPORTED_ALGORITHM(HttpStatus.UNPROCESSABLE_ENTITY, "AUTH_006", "Spring Security에서 지원하지 않는 공개키 알고리즘입니다.")
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
