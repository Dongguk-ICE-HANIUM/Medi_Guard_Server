package hanium.dongguk.auth.provider.apple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppleAuthTokenResponseDto (

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("expires_in")
        Integer expiresIn,

        @JsonProperty("id_token")
        String idToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("token_type")
        String tokenType
){
}
