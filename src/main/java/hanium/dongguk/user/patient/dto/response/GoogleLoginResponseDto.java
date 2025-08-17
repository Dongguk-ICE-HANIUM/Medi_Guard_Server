package hanium.dongguk.user.patient.dto.response;

import hanium.dongguk.global.dto.JwtDto;

import java.util.UUID;

public record GoogleLoginResponseDto(

        JwtDto jwtDto,
        boolean isSignUpNeeded,
        String userId
) {
    public static GoogleLoginResponseDto of(JwtDto jwtDto, boolean isSignUpNeeded, UUID userId) {
        return new GoogleLoginResponseDto(jwtDto, isSignUpNeeded, userId.toString());
    }
}
