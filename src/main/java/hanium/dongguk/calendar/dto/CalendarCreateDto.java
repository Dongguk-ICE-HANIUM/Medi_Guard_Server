package hanium.dongguk.calendar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CalendarCreateDto(
        @JsonProperty(value = "date")
        @NotNull
        @Schema(description = "날짜", example = "2025-08-04")
        LocalDate date,
        
        @JsonProperty(value = "description")
        @NotBlank
        @Schema(description = "설명", example = "오늘의 기분 설명을 작성해주세요.")
        String description,
        
        @JsonProperty(value = "emotion")
        @NotNull
        @Schema(description = "감정", example = "감정을 선택해주세요.")
        EEmotion emotion,
        
        @JsonProperty(value = "questionType")
        @NotNull
        @Schema(description = "질문 유형", example = "질문 유형을 선택해주세요.")
        EQuestionType questionType
) {
}