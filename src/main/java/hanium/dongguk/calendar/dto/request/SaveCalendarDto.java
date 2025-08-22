package hanium.dongguk.calendar.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "캘린더 생성 요청 DTO")
public record SaveCalendarDto(
        @JsonProperty("date")
        @NotNull(message = "날짜는 필수입니다.")
        @Schema(description = "날짜", example = "2025-08-04")
        LocalDate date,
        
        @JsonProperty("description")
        @NotBlank(message = "설명은 필수입니다.")
        @Schema(description = "기분 설명", example = "두통이 있고 어지럽다.")
        String description,
        
        @JsonProperty("emotion")
        @NotNull(message = "감정은 필수입니다.")
        @Schema(
                description = "감정 상태",
                example = "NEUTRAL",
                allowableValues = {
                        "VERY_HAPPY", "HAPPY", "NEUTRAL", "SAD", "ANGRY"
                }
        )
        EEmotion emotion,
        
        @JsonProperty("questionType")
        @NotNull(message = "질문 유형은 필수입니다.")
        @Schema(
                description = "질문 유형",
                example = "PHYSICAL_SYMPTOMS",
                allowableValues = {
                        "PHYSICAL_SYMPTOMS", "FETAL_MOVEMENT", "MEDICATION_COMPLIANCE",
                        "MEDICATION_SIDE_EFFECTS", "MOOD_STATUS", "MENTAL_HEALTH",
                        "DAILY_LIFE", "FAMILY_SUPPORT", "PATIENT_CONCERNS"
                }
        )
        EQuestionType questionType
) {
}