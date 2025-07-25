package hanium.dongguk.global.security.filter;

import hanium.dongguk.global.constants.Constants;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.global.exception.GlobalErrorCode;
import hanium.dongguk.global.security.info.JwtUserInfo;
import hanium.dongguk.global.security.provider.JwtAuthenticationManager;
import hanium.dongguk.global.util.HeaderUtil;
import hanium.dongguk.global.util.JwtUtil;
import hanium.dongguk.user.core.domain.ERole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationManager jwtAuthenticationManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Constants.NO_NEED_AUTH.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.info(request.getHeader(Constants.PREFIX_AUTH));
        String token = HeaderUtil.refineHeader(request, Constants.PREFIX_AUTH, Constants.PREFIX_BEARER)
                .orElseThrow(() -> CommonException.type(GlobalErrorCode.INVALID_HEADER_VALUE));

        Claims claims = jwtUtil.validateToken(token);
        log.info("claim: getUserId() = {}", UUID.fromString(claims.get(Constants.CLAIM_USER_ID, String.class)));

        // 클레임에서 사용자 정보 추출
        JwtUserInfo jwtUserInfo = new JwtUserInfo(
                UUID.fromString(claims.get(Constants.CLAIM_USER_ID, String.class)),
                ERole.valueOf(claims.get(Constants.CLAIM_USER_ROLE, String.class))
        );

        // 인증 받지 않은 인증용 객체 생성
        UsernamePasswordAuthenticationToken unAuthenticatedToken =
                new UsernamePasswordAuthenticationToken(jwtUserInfo, null, null);

        // 인증 받은 후의 인증 객체 생성
        UsernamePasswordAuthenticationToken authenticatedToken =
                (UsernamePasswordAuthenticationToken) jwtAuthenticationManager.authenticate(unAuthenticatedToken);
        log.info("인증 성공");

        // 사용자의 IP 등 세부 정보 인증 정보에 추가
        authenticatedToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticatedToken);
        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }
}
