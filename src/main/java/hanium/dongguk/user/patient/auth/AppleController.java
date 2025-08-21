package hanium.dongguk.user.patient.auth;

import hanium.dongguk.user.patient.auth.dto.AppleLoginRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hanium.dongguk.user.patient.dto.response.GoogleLoginResponseDto;

@RestController
@RequiredArgsConstructor
public class AppleController {

    private final AppleService appleService;

    @PostMapping("/api/auth/apple/login")
    public GoogleLoginResponseDto appleLogin (@RequestBody @Valid AppleLoginRequestDto request){
        return appleService.login(request);
    }
}
