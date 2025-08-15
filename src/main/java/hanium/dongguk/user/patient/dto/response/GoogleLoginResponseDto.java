package hanium.dongguk.user.patient.dto.response;

import hanium.dongguk.global.dto.JwtDto;

public record GoogleLoginResponseDto(

        JwtDto jwtDto,

        boolean isSignUpNeeded
) {
    public static GoogleLoginResponseDto of(JwtDto jwtDto, boolean isSignUpNeeded) {
        return new GoogleLoginResponseDto(jwtDto, isSignUpNeeded);
    }
}
