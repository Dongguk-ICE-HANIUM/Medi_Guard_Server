package hanium.dongguk.auth.provider.apple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppleAuthTokenResponseDto (

        @JsonProperty("accessToken")
        String accessToken,

        @JsonProperty("expiresIn")
        Integer expiresIn,

        @JsonProperty("idToken")
        String idToken,

        @JsonProperty("refreshToken")
        String refreshToken,

        @JsonProperty("tokenType")
        String tokenType
){
}
