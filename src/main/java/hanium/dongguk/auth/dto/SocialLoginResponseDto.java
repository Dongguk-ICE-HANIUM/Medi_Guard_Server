package hanium.dongguk.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.global.dto.JwtDto;

import java.util.UUID;

public record SocialLoginResponseDto(

        @JsonProperty("jwtDto")
        JwtDto jwtDto,

        @JsonProperty("isSignUpNeeded")
        boolean isSignUpNeeded,

        @JsonProperty("userId")
        String userId
) {
    public static SocialLoginResponseDto of(JwtDto jwtDto, boolean isSignUpNeeded, UUID userId) {
        return new SocialLoginResponseDto(jwtDto, isSignUpNeeded, userId.toString());
    }
}
