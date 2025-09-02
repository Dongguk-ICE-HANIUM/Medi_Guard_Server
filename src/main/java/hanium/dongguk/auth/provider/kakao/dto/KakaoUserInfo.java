package hanium.dongguk.auth.provider.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfo(

        @JsonProperty("id")
        Long id,

        @JsonProperty("kakaoAccount")
        KakaoAccount kakaoAccount
) {
    public String providerId(){
        return id.toString();
    }

    public String email(){
        return kakaoAccount.email();
    }
}
