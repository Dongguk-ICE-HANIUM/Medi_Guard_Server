package hanium.dongguk.user.patient.service;

import hanium.dongguk.auth.oauth.GoogleOauthClient;
import hanium.dongguk.auth.oauth.GoogleUserInfo;
import hanium.dongguk.global.dto.JwtDto;
import hanium.dongguk.global.util.JwtUtil;
import hanium.dongguk.user.core.domain.EStatus;
import hanium.dongguk.user.core.domain.User;
import hanium.dongguk.user.core.service.UserRetriever;
import hanium.dongguk.user.core.service.UserSaver;
import hanium.dongguk.user.core.validator.UserValidator;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.domain.UserPatientRepository;
import hanium.dongguk.user.patient.dto.request.GoogleLoginRequestDto;
import hanium.dongguk.user.patient.dto.request.SocialLoginSignupRequestDto;
import hanium.dongguk.user.patient.dto.response.GoogleLoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleAuthService {

    private final UserPatientRepository userPatientRepository;
    private final GoogleOauthClient googleOauthClient;
    private final UserRetriever userRetriever;
    private final UserSaver userSaver;
    private final JwtUtil jwtUtil;
    private final UserValidator userValidator;
    private final UserPatientRetriever userPatientRetriever;

    @Transactional
    public GoogleLoginResponseDto googleLogin(GoogleLoginRequestDto request) {
        log.info("Google 로그인 시작");

        GoogleUserInfo userInfo = googleOauthClient.getUserInfo(request.accessToken());
        User user = userRetriever.getUserBySerialId(userInfo.providerId())
                .orElseGet(() -> {
                    UserPatient userPatient = UserPatient.googleCreate(userInfo.providerId(), userInfo.name());
                    userSaver.save(userPatient);
                    return userPatient;
                });

        if(userValidator.isInactive(user.getStatus()))
        {
            user.activate();
        }


        return createLoginResponse(user);
    }

    @Transactional
    public JwtDto socialLoginSignup(SocialLoginSignupRequestDto request){
        UserPatient userPatient = userPatientRetriever.getUserPatient(request.userId());

        userValidator.validatePending(userPatient.getStatus());

        LocalDate birthday = LocalDate.parse(request.birthday());
        LocalDate dueDate = LocalDate.parse(request.dueDate());

        userValidator.validateBirthday(birthday);
        userValidator.validateDueDate(dueDate);

        userPatient.update(request.name(),
                birthday,
                request.height(),
                request.weight(),
                dueDate,
                request.pregnancyWeek(),
                request.feeding());

        userPatient.activate();

        return jwtUtil.generateTokens(request.userId(), userPatient.getRole());

    }

    private GoogleLoginResponseDto createLoginResponse(User user){
        return user.getStatus() == EStatus.PENDING
                ? GoogleLoginResponseDto.of(null, true, user.getId())
                : GoogleLoginResponseDto.of(jwtUtil.generateTokens(user.getId(), user.getRole()),false, user.getId());
    }



}
