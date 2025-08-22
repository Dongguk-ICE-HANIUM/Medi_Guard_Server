package hanium.dongguk.auth.provider.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.dongguk.auth.exception.AuthErrorCode;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.auth.provider.apple.dto.ApplePublicKeyList;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleJwtTokenValidator {

    private static final String TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final AppleApiService appleApiService;
    private final AppleOauthManager appleOauthManager;

    public String getAppleSerialId(String identityToken){
        Map<String, String> headers = parseHeaders(identityToken);
        ApplePublicKeyList response = appleApiService.getAppleAuthPublicKey();
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
}
