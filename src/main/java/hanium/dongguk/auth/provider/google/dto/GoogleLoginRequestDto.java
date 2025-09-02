package hanium.dongguk.auth.provider.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleLoginRequestDto(

        @JsonProperty("access_token")
        String accessToken
) {
}
