package hanium.dongguk.global.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import hanium.dongguk.global.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static hanium.dongguk.global.exception.GlobalErrorCode.SUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"errorCode", "message", "result"})
public class ResponseDto<T> {
    private final String errorCode;
    private final String message;
    private T result;

    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>(null, "SUCCESS", data);
    }

    public static <T> ResponseDto<T> fail(ErrorResponse errorResponse) {
        return new ResponseDto<>(errorResponse.getErrorCode(), errorResponse.getMessage(), null);
    }

    public ResponseDto(T result) {
        this.errorCode = SUCCESS.getErrorCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }
}
