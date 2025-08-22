package hanium.dongguk.auth.dto;

import hanium.dongguk.global.dto.JwtDto;

import java.util.UUID;

public record SocialLoginResponseDto(

        JwtDto jwtDto,
        boolean isSignUpNeeded,
        String userId
) {
    public static SocialLoginResponseDto of(JwtDto jwtDto, boolean isSignUpNeeded, UUID userId) {
        return new SocialLoginResponseDto(jwtDto, isSignUpNeeded, userId.toString());
    }
}
