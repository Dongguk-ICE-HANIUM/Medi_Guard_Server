package hanium.dongguk.global.security.provider;

import hanium.dongguk.global.security.info.JwtUserInfo;
import hanium.dongguk.global.security.info.UserPrincipal;
import hanium.dongguk.global.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("AuthenticationProvider 진입 성공");
        if (authentication.getPrincipal().getClass().equals(String.class)) {
            log.info("로그인 로직 인증 과정");
            return authOfLogin(authentication);
        } else {
            log.info("로그인 한 사용자 검증 과정");
            return authOfAfterLogin((JwtUserInfo) authentication.getPrincipal());
        }
    }

    private Authentication authOfLogin(Authentication authentication){
        // DB에 저장된 실제 데이터
        UserPrincipal userPrincipal = customUserDetailService.loadUserByUsername(authentication.getPrincipal().toString());

        // 비밀번호 검증 로직
        if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), userPrincipal.getPassword())) {
            throw new UsernameNotFoundException("비밀번호가 일치하지 않습니다 ! ");
        }
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    private Authentication authOfAfterLogin(JwtUserInfo userInfo){
        UserPrincipal userPrincipal = customUserDetailService.loadUserById(userInfo.userId());
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
