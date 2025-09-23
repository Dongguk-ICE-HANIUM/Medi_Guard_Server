package hanium.dongguk.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.schedule.domain.Schedule;

import java.util.UUID;

public record VerifyCodeResponseDto(
        @JsonProperty("scheduleId")
        UUID scheduleId
) {
        public static VerifyCodeResponseDto from(Schedule schedule) {
                return new VerifyCodeResponseDto(schedule.getId());
        }
}
