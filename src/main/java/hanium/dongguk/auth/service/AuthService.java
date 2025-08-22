package hanium.dongguk.auth.service;

import hanium.dongguk.auth.exception.AuthErrorCode;
import hanium.dongguk.auth.provider.apple.AppleJwtTokenValidator;
import hanium.dongguk.auth.provider.google.GoogleApiService;
import hanium.dongguk.auth.provider.google.dto.GoogleUserInfo;
import hanium.dongguk.global.dto.JwtDto;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.global.util.JwtUtil;
import hanium.dongguk.user.core.domain.EProvider;
import hanium.dongguk.user.core.domain.EStatus;
import hanium.dongguk.user.core.domain.Email;
import hanium.dongguk.user.core.domain.User;
import hanium.dongguk.auth.dto.NormalRegisterRequestDto;
import hanium.dongguk.user.core.service.UserRetriever;
import hanium.dongguk.user.core.service.UserSaver;
import hanium.dongguk.auth.validator.AuthValidator;
import hanium.dongguk.auth.provider.apple.dto.AppleLoginRequestDto;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.auth.provider.google.dto.GoogleLoginRequestDto;
import hanium.dongguk.auth.dto.SocialLoginSignupRequestDto;
import hanium.dongguk.auth.dto.SocialLoginResponseDto;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final GoogleApiService googleApiService;
    private final UserRetriever userRetriever;
    private final UserSaver userSaver;
    private final JwtUtil jwtUtil;
    private final AuthValidator authValidator;
    private final UserPatientRetriever userPatientRetriever;
    private final AppleJwtTokenValidator appleJwtTokenValidator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void normalRegister(NormalRegisterRequestDto request)
    {

        LocalDate birthday = LocalDate.parse(request.birthday());
        LocalDate dueDate = LocalDate.parse(request.dueDate());

        authValidator.validateBirthday(birthday);
        authValidator.validateDueDate(dueDate);
        validateEmailNotExist(request.email());


        UserPatient userPatient = UserPatient.normalCreate(
                request.email(),
                encodePassword(request.password()),
                request.name(),
                birthday,
                request.height(),
                request.weight(),
                dueDate,
                request.pregnancyWeek(),
                request.feeding());

        userSaver.save(userPatient);
    }



    @Transactional
    public SocialLoginResponseDto googleLogin(GoogleLoginRequestDto request) {
        log.info("Google 로그인 시작");

        GoogleUserInfo userInfo = googleApiService.getUserInfo(request.accessToken());
        User user = userRetriever.getUserBySerialId(userInfo.providerId())
                .orElseGet(() -> {
                    UserPatient userPatient = UserPatient.socialCreate(userInfo.providerId(),
                                                                       userInfo.name(),
                                                                       EProvider.GOOGLE);
                    userSaver.save(userPatient);
                    return userPatient;
                });

        if(authValidator.isInactive(user.getStatus()))
        {
            user.activate();
        }


        return createLoginResponse(user);
    }

    @Transactional
    public JwtDto socialLoginSignup(SocialLoginSignupRequestDto request){
        UserPatient userPatient = userPatientRetriever.getUserPatient(request.userId());

        authValidator.validatePending(userPatient.getStatus());

        LocalDate birthday = LocalDate.parse(request.birthday());
        LocalDate dueDate = LocalDate.parse(request.dueDate());

        authValidator.validateBirthday(birthday);
        authValidator.validateDueDate(dueDate);

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

    @Transactional
    public SocialLoginResponseDto login(AppleLoginRequestDto request){
        String appleSerialId = appleJwtTokenValidator.getAppleSerialId(request.idToken());

        User user = userRetriever.getUserBySerialId(appleSerialId)
                .orElseGet(() -> {
                    UserPatient userPatient = UserPatient.socialCreate(appleSerialId, null, EProvider.APPLE);
                    userSaver.save(userPatient);
                    return userPatient;
                });

        if(authValidator.isInactive(user.getStatus()))
        {
            user.activate();
        }

        return createLoginResponse(user);
    }


    private SocialLoginResponseDto createLoginResponse(User user){
        return user.getStatus() == EStatus.PENDING
                ? SocialLoginResponseDto.of(null, true, user.getId())
                : SocialLoginResponseDto.of(jwtUtil.generateTokens(user.getId(), user.getRole()),false, user.getId());
    }

    private void validateEmailNotExist(Email email){
        if(userRetriever.existsByEmail(email)){
            throw CommonException.type(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    // == password 암호화 == //
    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

}
