package hanium.dongguk.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record SaveScheduleRequestDto(

        @JsonProperty("scheduleTime")
        @Schema(
                description = "진료 예정 시간", 
                example = "2024-10-15T14:30:00"
        )
        LocalDateTime scheduleTime,

        @JsonProperty("doctorId")
        @Schema(description = "약속을 잡은 doctor의 UUID",
        example = "ab36bc1a-8ece-11f0-80f6-00155da312b9")
        UUID doctorId

) {
}
