package hanium.dongguk.global.security.info;

import hanium.dongguk.global.constants.Constants;
import hanium.dongguk.global.dto.JwtDto;
import hanium.dongguk.global.exception.ErrorCode;
import hanium.dongguk.global.exception.GlobalErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthenticationResponse {
    public static void makeLoginSuccessResponse(
            HttpServletResponse response,
            JwtDto jwtDto,
            Integer refreshExpiration
    ) throws IOException {
        makeResponse(response, GlobalErrorCode.SUCCESS, jwtDto);
    }

    public static void makeSuccessResponse(HttpServletResponse response) throws IOException {
        makeResponse(response, GlobalErrorCode.SUCCESS, null);
    }

    public static void makeFailureResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        makeResponse(response, errorCode, null);
    }

    private static void makeResponse(
            HttpServletResponse response,
            ErrorCode errorCode,
            Object result
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());

        Map<String, Object> body = new HashMap<>();
        body.put("errorCode", errorCode.getErrorCode());
        body.put("message", errorCode.getMessage());
        body.put("result", result);

        response.getWriter().write(JSONValue.toJSONString(body));
    }
}
