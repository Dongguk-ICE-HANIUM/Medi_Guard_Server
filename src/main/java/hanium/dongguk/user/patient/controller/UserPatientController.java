package hanium.dongguk.user.patient.controller;

import hanium.dongguk.user.patient.dto.request.GoogleLoginRequestDto;
import hanium.dongguk.user.patient.dto.response.GoogleLoginResponseDto;
import hanium.dongguk.user.patient.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserPatientController implements UserPatientApiSwagger {

    private final GoogleAuthService googleAuthService;

    @PostMapping("/google/login")
    public GoogleLoginResponseDto googleLogin(@RequestBody GoogleLoginRequestDto googleLoginRequestDto) {
        return googleAuthService.googleLogin(googleLoginRequestDto);
    }

}
