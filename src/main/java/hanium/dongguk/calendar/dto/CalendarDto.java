package hanium.dongguk.calendar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.EEmotion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CalendarDto(
        @JsonProperty(value = "id")
        @Schema(description = "캘린더 ID", example = "캘린더 ID를 작성해주세요.")
        UUID id,
        
        @JsonProperty(value = "date")
        @NotNull
        @Schema(description = "날짜", example = "2025-08-04")
        LocalDate date,
        
        @JsonProperty(value = "description")
        @Schema(description = "설명", example = "오늘의 기분 설명을 작성해주세요.")
        String description,
        
        @JsonProperty(value = "emotion")
        @NotNull
        @Schema(description = "감정", example = "감정을 선택해주세요.")
        EEmotion emotion
) {
    public static CalendarDto of(Calendar calendar) {
        return new CalendarDto(
                calendar.getId(),
                calendar.getDate(),
                calendar.getDescription(),
                calendar.getEmotion()
        );
    }
}