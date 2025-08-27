package hanium.dongguk.auth.provider.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoAccount(

        @JsonProperty("email")
        String email
) {
}
