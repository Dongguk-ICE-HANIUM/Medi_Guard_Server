package hanium.dongguk.global.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final ErrorCode code;

    public CommonException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public static CommonException type(ErrorCode code) {
        return new CommonException(code);
    }
}