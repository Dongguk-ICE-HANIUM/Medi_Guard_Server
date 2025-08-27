package hanium.dongguk.question.exception;

import hanium.dongguk.global.exception.ErrorCode;
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
    EMPTY_ANSWER(HttpStatus.BAD_REQUEST, "QUESTION_003", "답변을 입력해주세요."),

    QUESTION_NOT_FOUND_FOR_TYPE(HttpStatus.NOT_FOUND, "QUESTION_004", "해당하는 유형 질문이 없습니다."),

    QUESTIONS_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "QUESTION_005", "이미 질문이 존재합니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}