package hanium.dongguk.user.patient.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.global.util.JwtUtil;
import hanium.dongguk.user.core.domain.EStatus;
import hanium.dongguk.user.core.domain.User;
import hanium.dongguk.user.core.service.UserRetriever;
import hanium.dongguk.user.core.service.UserSaver;
import hanium.dongguk.user.core.validator.UserValidator;
import hanium.dongguk.user.patient.auth.dto.AppleLoginRequestDto;
import hanium.dongguk.user.patient.auth.dto.ApplePublicKeysResponseDto;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.exception.AuthErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import hanium.dongguk.user.patient.dto.response.GoogleLoginResponseDto;

import java.security.PublicKey;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppleService {

    private static final String TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final AppleApiService appleApiService;
    private final AppleOauthManager appleOauthManager;
    private final UserRetriever userRetriever;
    private final UserSaver userSaver;
    private final UserValidator userValidator;
    private final JwtUtil jwtUtil;


    public GoogleLoginResponseDto login(AppleLoginRequestDto request){
        String appleSerialId = getAppleSerialId(request.idToken());

        User user = userRetriever.getUserBySerialId(appleSerialId)
                .orElseGet(() -> {
                    UserPatient userPatient = UserPatient.googleCreate(appleSerialId, null);
                    userSaver.saveUser(userPatient);
                    return userPatient;
                });

        if(userValidator.isInactive(user.getStatus()))
        {
            user.activate();
        }

        return createLoginResponse(user);
    }

    private String getAppleSerialId(String identityToken){
        Map<String, String> headers = parseHeaders(identityToken);
        ApplePublicKeysResponseDto response = appleApiService.getAppleAuthPublicKey();
        PublicKey publicKey = appleOauthManager.generatePublicKey(headers, response);

        return getTokenClaims(identityToken, publicKey).getSubject();
    }

    private Map<String, String> parseHeaders(String identityToken){
        String encodedHeader = identityToken.split(TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
        String decodedHeader = new String(Base64.decodeBase64(encodedHeader));

        try{
            return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
        } catch(JsonProcessingException e){
            throw CommonException.type(AuthErrorCode.CANNOT_JSON_PROCESS);
        }
    }

    private Claims getTokenClaims(String token, PublicKey publicKey){
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private GoogleLoginResponseDto createLoginResponse(User user){
        return user.getStatus() == EStatus.PENDING
                ? GoogleLoginResponseDto.of(null, true, user.getId())
                : GoogleLoginResponseDto.of(jwtUtil.generateTokens(user.getId(), user.getRole()),false, user.getId());
    }

}
