package hanium.dongguk.auth.provider.apple.dto;

import jakarta.validation.constraints.NotBlank;

public record AppleLoginRequestDto(

        @NotBlank
        String state,

        @NotBlank
        String code,

        @NotBlank
        String idToken
) {
}
