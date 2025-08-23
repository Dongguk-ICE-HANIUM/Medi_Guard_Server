package hanium.dongguk.notification.taking.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.notification.taking.domain.NotifiTaking;
import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Builder
public record NotifiTakingResponseDto(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("time")
        LocalTime time,

        @JsonProperty("isActive")
        Boolean isActive
){
    public static NotifiTakingResponseDto from(
            final NotifiTaking notifiTaking) {
        return NotifiTakingResponseDto.builder()
                .id(notifiTaking.getId())
                .time(notifiTaking.getTakingTime())
                .isActive(notifiTaking.isActive())
                .build();
    }
}
