package hanium.dongguk.user.patient.auth.dto;

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
