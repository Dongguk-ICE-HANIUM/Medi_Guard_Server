package hanium.dongguk.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum QuestionErrorCode implements ErrorCode {

    // 질문이 존재하지 않을 때
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "QUESTION_001", "존재하지 않는 질문입니다."),

    // 잘못된 질문 타입일 때
    INVALID_QUESTION_TYPE(HttpStatus.BAD_REQUEST, "QUESTION_002", "유효하지 않은 질문 타입입니다."),

    // 답변이 비어 있을 때 (필요하다면)
    EMPTY_ANSWER(HttpStatus.BAD_REQUEST, "QUESTION_003", "답변을 입력해주세요.");

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