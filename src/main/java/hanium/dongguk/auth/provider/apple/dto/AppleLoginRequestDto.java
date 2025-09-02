package hanium.dongguk.auth.provider.apple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AppleLoginRequestDto(

        @NotBlank
        @JsonProperty("state")
        String state,

        @NotBlank
        @JsonProperty("code")
        String code,

        @NotBlank
        @JsonProperty("idToken")
        String idToken
) {
}
