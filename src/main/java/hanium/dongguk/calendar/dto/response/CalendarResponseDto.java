package hanium.dongguk.calendar.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.EEmotion;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "캘린더 응답 DTO")
public record CalendarResponseDto(
        @JsonProperty("emotion")
        @Schema(description = "감정 상태", example = "NEUTRAL")
        EEmotion emotion,
        
        @JsonProperty("description")
        @Schema(description = "기분 설명", example = "두통이 있고 어지럽다.")
        String description
) {
    public static CalendarResponseDto of(Calendar calendar) {
        return new CalendarResponseDto(
                calendar.getEmotion(),
                calendar.getDescription()
        );
    }
}