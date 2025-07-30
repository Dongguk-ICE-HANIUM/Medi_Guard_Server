package hanium.dongguk.global.dto;

import lombok.Builder;

@Builder
public record JwtDto(
        String accessToken,
        String refreshToken
) {
    public static JwtDto of(String accessToken, String refreshToken){
        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
