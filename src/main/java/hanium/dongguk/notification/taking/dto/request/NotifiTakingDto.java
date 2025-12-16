package hanium.dongguk.notification.taking.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

@Schema(description = "약물 알림 정보")
public record NotifiTakingDto(
        @Schema(description = "알림 ID (수정 시 필수)", example = "123e4567-e89b-12d3-a456-426614174000", nullable = true)
        @JsonProperty("id")
        UUID id,

        @Schema(description = "알림 시간", example = "08:00:00", required = true)
        @NotNull(message = "약물 알림 시간이 누락되었습니다.")
        @JsonProperty("time")
        LocalTime time,

        @Schema(description = "알림 활성화 여부", example = "true", required = true)
        @NotNull(message = "약물 알림 활성화 여부가 누락되었습니다.")
        @JsonProperty("isActive")
        Boolean isActive
) {
}