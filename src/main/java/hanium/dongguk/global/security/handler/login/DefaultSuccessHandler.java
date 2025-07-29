package hanium.dongguk.global.security.handler.login;

import hanium.dongguk.auth.jwt.domain.RefreshToken;
import hanium.dongguk.auth.jwt.domain.RefreshTokenRepository;
import hanium.dongguk.global.dto.JwtDto;
import hanium.dongguk.global.security.info.AuthenticationResponse;
import hanium.dongguk.global.security.info.UserPrincipal;
import hanium.dongguk.global.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class DefaultSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        JwtDto jwtDto = jwtUtil.generateTokens(
                                userPrincipal.getUserId(),
                                userPrincipal.getRole()
                       );

        refreshTokenRepository.save(
                RefreshToken.issueRefreshToken(
                        userPrincipal.getUserId(),
                        jwtDto.refreshToken()
                )
        );

        AuthenticationResponse.makeLoginSuccessResponse(
                response,
                jwtDto,
                jwtUtil.getRefreshExpiration()
        );
    }
}
