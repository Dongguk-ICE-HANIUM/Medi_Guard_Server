package hanium.dongguk.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.global.dto.JwtDto;

import java.util.UUID;

public record SocialLoginResponseDto(

        @JsonProperty("jwt_dto")
        JwtDto jwtDto,

        @JsonProperty("is_sign_up_needed")
        boolean isSignUpNeeded,

        @JsonProperty("user_id")
        String userId
) {
    public static SocialLoginResponseDto of(JwtDto jwtDto, boolean isSignUpNeeded, UUID userId) {
        return new SocialLoginResponseDto(jwtDto, isSignUpNeeded, userId.toString());
    }
}
