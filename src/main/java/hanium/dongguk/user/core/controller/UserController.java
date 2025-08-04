package hanium.dongguk.user.core.controller;

import hanium.dongguk.user.core.dto.request.NormalRegisterRequestDto;
import hanium.dongguk.user.core.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController implements UserApi {


    private final UserService userService;

    @Override
    @PostMapping("/user")
    public void normalRegister(@RequestBody @Valid NormalRegisterRequestDto request) {
        userService.normalRegister(request);
    }

}
