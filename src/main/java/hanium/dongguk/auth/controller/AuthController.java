package hanium.dongguk.auth.controller;

import hanium.dongguk.auth.service.AuthService;
import hanium.dongguk.global.dto.JwtDto;
import hanium.dongguk.auth.provider.apple.dto.AppleLoginRequestDto;
import hanium.dongguk.auth.provider.google.dto.GoogleLoginRequestDto;
import hanium.dongguk.auth.dto.SocialLoginSignupRequestDto;
import hanium.dongguk.auth.dto.SocialLoginResponseDto;
import hanium.dongguk.auth.dto.NormalRegisterRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController implements AuthApiSwagger {

    private final AuthService authService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<Void> normalRegister(@RequestBody @Valid NormalRegisterRequestDto request) {
        authService.normalRegister(request);
        return ResponseEntity.ok().build();
    }

    @Override
    @PostMapping("/google/login")
    public ResponseEntity<SocialLoginResponseDto> googleLogin(@RequestBody GoogleLoginRequestDto request) {
        return ResponseEntity.ok(authService.googleLogin(request));
    }

    @Override
    @PostMapping("/social/signup")
    public ResponseEntity<JwtDto> socialLoginSignup(@RequestBody SocialLoginSignupRequestDto request) {
        return ResponseEntity.ok(authService.socialLoginSignup(request));
    }

    @Override
    @PostMapping("/apple/login")
    public ResponseEntity<SocialLoginResponseDto> appleLogin (@RequestBody @Valid AppleLoginRequestDto request){
        return ResponseEntity.ok(authService.login(request));
    }
}
