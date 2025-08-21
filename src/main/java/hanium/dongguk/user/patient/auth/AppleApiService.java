package hanium.dongguk.user.patient.auth;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.patient.auth.dto.AppleAuthTokenResponseDto;
import hanium.dongguk.user.patient.auth.dto.ApplePublicKeysResponseDto;
import hanium.dongguk.user.patient.exception.AuthErrorCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AppleApiService {
    private final WebClient appleWebClient;

    public AppleApiService(@Qualifier("appleWebClient") WebClient appleWebClient) {
        this.appleWebClient = appleWebClient;
    }

    public AppleAuthTokenResponseDto generateToken(String code, String clientId, String clientSecret, String grantType){

        return appleWebClient.post()
                .uri("/token")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("code", code)
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("grant_type", grantType))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(CommonException.type(AuthErrorCode.APPLE_TOKEN_REQUEST_FAILED)))
                .bodyToMono(AppleAuthTokenResponseDto.class)
                .block();
    }

    public ApplePublicKeysResponseDto getAppleAuthPublicKey(){

        return appleWebClient.get()
                .uri("/keys")
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(CommonException.type(AuthErrorCode.APPLE_PUBLIC_KEY_REQUEST_FAILED)))
                .bodyToMono(ApplePublicKeysResponseDto.class)
                .block();

    }


}
