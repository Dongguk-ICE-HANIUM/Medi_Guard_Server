package hanium.dongguk.global.security.handler.logout;

import hanium.dongguk.global.security.info.AuthenticationResponse;
import hanium.dongguk.user.core.exception.UserErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        if (authentication == null) {
            log.info("인증 정보가 존재하지 않습니다. authentication is null.");
            AuthenticationResponse.makeFailureResponse(response, UserErrorCode.NOT_FOUND_USER);
        }
        AuthenticationResponse.makeSuccessResponse(response);
    }
}
