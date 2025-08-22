package hanium.dongguk.notification.taking.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record NotifiTakingDto(
        @JsonProperty("id")
        UUID id,

        @NotNull(message = "약물 알림 시간이 누락되었습니다.")
        @JsonProperty("time")
        LocalTime time,

        @NotNull(message = "약물 알림 활성화 여부가 누락되었습니다.")
        @JsonProperty("isActive")
        Boolean isActive
) {
}
