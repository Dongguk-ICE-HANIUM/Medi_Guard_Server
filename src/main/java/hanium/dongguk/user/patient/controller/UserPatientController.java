package hanium.dongguk.user.patient.controller;

import hanium.dongguk.global.dto.JwtDto;
import hanium.dongguk.user.patient.dto.request.GoogleLoginRequestDto;
import hanium.dongguk.user.patient.dto.request.SocialLoginSignupRequestDto;
import hanium.dongguk.user.patient.dto.response.GoogleLoginResponseDto;
import hanium.dongguk.user.patient.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserPatientController implements UserPatientApiSwagger {

    private final GoogleAuthService googleAuthService;

    @PostMapping("/google/login")
    public GoogleLoginResponseDto googleLogin(@RequestBody GoogleLoginRequestDto googleLoginRequestDto) {
        return googleAuthService.googleLogin(googleLoginRequestDto);
    }

    @PostMapping("/social/signup")
    public JwtDto socialLoginSignup(@RequestBody SocialLoginSignupRequestDto request) {
        return googleAuthService.socialLoginSignup(request);
    }
}
