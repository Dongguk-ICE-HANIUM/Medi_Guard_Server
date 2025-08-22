package hanium.dongguk.auth.provider.apple;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.auth.provider.apple.dto.ApplePublicKey;
import hanium.dongguk.auth.provider.apple.dto.ApplePublicKeyList;
import hanium.dongguk.auth.exception.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppleOauthManager {

    public PublicKey generatePublicKey (Map<String, String> tokenHeaders, ApplePublicKeyList response){
        ApplePublicKey applePublicKey = getMatchedKey(tokenHeaders.get("kid"), tokenHeaders.get("alg"), response);

        return getPublicKey(applePublicKey);
    }

    private ApplePublicKey getMatchedKey (String kid, String alg, ApplePublicKeyList response){
        return response.keyList().stream()
                .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                .findAny()
                .orElseThrow(() -> CommonException.type(AuthErrorCode.NOT_MATCHED_PUBLIC_KEY));
    }

    private PublicKey getPublicKey (ApplePublicKey applePublicKey){
        byte[] nBytes = Base64.getDecoder().decode(applePublicKey.n());
        byte[] eBytes = Base64.getDecoder().decode(applePublicKey.e());

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, nBytes),
                                                              new BigInteger(1, eBytes));

        KeyFactory keyFactory;

        try{
            keyFactory = KeyFactory.getInstance(applePublicKey.kty());
        } catch(NoSuchAlgorithmException e){
            throw CommonException.type(AuthErrorCode.NOT_FOUND_ALGORITHM);
        }

        try{
            return keyFactory.generatePublic(publicKeySpec);
        } catch(InvalidKeySpecException e){
            throw CommonException.type(AuthErrorCode.NOT_SUPPORTED_ALGORITHM);
        }

    }

}
