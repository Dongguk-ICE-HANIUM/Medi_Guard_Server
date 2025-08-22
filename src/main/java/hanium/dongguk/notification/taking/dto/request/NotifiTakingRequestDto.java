package hanium.dongguk.notification.taking.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NotifiTakingRequestDto(
        @NotNull(message = "약물 알림 리스트가 누락되었습니다.")
        @JsonProperty("notifiTakingList")
        List<NotifiTakingDto> notifiTakingDtoList
) {
}
