package hanium.dongguk.auth.provider.kakao;

import hanium.dongguk.auth.exception.AuthErrorCode;
import hanium.dongguk.auth.provider.kakao.dto.KakaoUserInfo;
import hanium.dongguk.global.constants.Constants;
import hanium.dongguk.global.exception.CommonException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoApiService {

    private final WebClient webClient;

    public KakaoApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(Constants.KAKAO_BASEURL)
                .build();
    }

    public KakaoUserInfo getUserInfo(String accessToken) {
        try{
            KakaoUserInfo userInfo = webClient.get()
                    .uri("/user/me")
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(KakaoUserInfo.class)
                    .block();

            if (userInfo == null) {
                throw CommonException.type(AuthErrorCode.NOT_FOUND_KAKAO_USER);
            }
            return userInfo;
        } catch (Exception e){
            throw CommonException.type(AuthErrorCode.KAKAO_OAUTH_FAILED);
        }
    }
}
