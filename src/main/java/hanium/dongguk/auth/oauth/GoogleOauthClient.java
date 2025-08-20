package hanium.dongguk.auth.oauth;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.core.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GoogleOauthClient {

    private final WebClient webClient;

    public GoogleUserInfo getUserInfo(String accessToken) {
        try{
            GoogleUserInfo userInfo = webClient.get()
                    .uri("/userinfo?access_token={token}", accessToken)
                    .retrieve()
                    .bodyToMono(GoogleUserInfo.class)
                    .block();

            if(userInfo == null)
            {
                throw CommonException.type(UserErrorCode.NOT_FOUND_GOOGLE_USER);
            }

            return userInfo;
        } catch (Exception e){
            throw CommonException.type(UserErrorCode.GOOGLE_OAUTH_FAILED);
        }
    }
}
