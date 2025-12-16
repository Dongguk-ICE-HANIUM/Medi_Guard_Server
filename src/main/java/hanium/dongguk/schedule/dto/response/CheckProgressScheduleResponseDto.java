package hanium.dongguk.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.schedule.domain.Schedule;

public record CheckProgressScheduleResponseDto(
    @JsonProperty("isProgress")
    boolean isProgress
) {
    public static CheckProgressScheduleResponseDto progress() {
        return new CheckProgressScheduleResponseDto(true);
    }

    public static CheckProgressScheduleResponseDto started() {
        return new CheckProgressScheduleResponseDto(false);
    }
}
