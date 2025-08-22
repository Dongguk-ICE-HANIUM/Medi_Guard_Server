package hanium.dongguk.auth.provider.google;

import hanium.dongguk.auth.exception.AuthErrorCode;
import hanium.dongguk.auth.provider.google.dto.GoogleUserInfo;
import hanium.dongguk.global.constants.Constants;
import hanium.dongguk.global.exception.CommonException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleApiService {

    private final WebClient webClient;

    public GoogleApiService(WebClient webClient) {
        this.webClient = webClient.mutate()
                .baseUrl(Constants.GOOGLE_BASEURL)
                .build();
    }

    public GoogleUserInfo getUserInfo(String accessToken) {
        try{
            GoogleUserInfo userInfo = webClient.get()
                    .uri("/userinfo?access_token={token}", accessToken)
                    .retrieve()
                    .bodyToMono(GoogleUserInfo.class)
                    .block();

            if(userInfo == null)
            {
                throw CommonException.type(AuthErrorCode.NOT_FOUND_GOOGLE_USER);
            }

            return userInfo;
        } catch (Exception e){
            throw CommonException.type(AuthErrorCode.GOOGLE_OAUTH_FAILED);
        }
    }
}
