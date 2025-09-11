package hanium.dongguk.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record SaveScheduleRequestDto(

        @JsonProperty("scheduleTime")
        @Schema(
                description = "진료 예정 시간", 
                example = "2024-10-15T14:30:00"
        )
        LocalDateTime scheduleTime
) {
}
