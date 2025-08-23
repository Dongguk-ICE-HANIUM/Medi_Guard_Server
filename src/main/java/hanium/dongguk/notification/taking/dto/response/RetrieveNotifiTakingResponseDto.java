package hanium.dongguk.notification.taking.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RetrieveNotifiTakingResponseDto(
        List<NotifiTakingResponseDto> notifiTakingDtoList
) {
    public static RetrieveNotifiTakingResponseDto of(
            List<NotifiTakingResponseDto> notifiTakingDtoList
    ){
        return RetrieveNotifiTakingResponseDto.builder()
                .notifiTakingDtoList(notifiTakingDtoList)
                .build();
    }
}
