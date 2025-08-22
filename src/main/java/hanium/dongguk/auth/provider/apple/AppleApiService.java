package hanium.dongguk.auth.provider.apple;

import hanium.dongguk.auth.exception.AuthErrorCode;
import hanium.dongguk.auth.provider.apple.dto.AppleAuthTokenResponseDto;
import hanium.dongguk.auth.provider.apple.dto.ApplePublicKeyList;
import hanium.dongguk.global.constants.Constants;
import hanium.dongguk.global.exception.CommonException;
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

    public AppleApiService(WebClient webClient) {
        this.appleWebClient = webClient.mutate()
                .baseUrl(Constants.APPLE_BASEURL)
                .build();
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

    public ApplePublicKeyList getAppleAuthPublicKey(){

        return appleWebClient.get()
                .uri("/keys")
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(CommonException.type(AuthErrorCode.APPLE_PUBLIC_KEY_REQUEST_FAILED)))
                .bodyToMono(ApplePublicKeyList.class)
                .block();

    }


}
