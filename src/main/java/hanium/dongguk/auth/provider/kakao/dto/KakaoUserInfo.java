package hanium.dongguk.auth.provider.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfo(

        Long id,

        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
    public String providerId(){
        return id.toString();
    }

    public String email(){
        return kakaoAccount.email();
    }
}
