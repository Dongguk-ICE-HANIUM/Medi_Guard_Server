package hanium.dongguk.question.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.question.domain.EQuestionType;
import hanium.dongguk.question.domain.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record QuestionDto(
        @JsonProperty(value = "id")
        @Schema(description = "질문 ID", example = "질문 ID을 작성해주세요.")
        UUID id,

        @JsonProperty(value = "type")
        @NotNull
        @Schema(
                description = "질문 유형",
                example = "PHYSICAL_SYMPTOMS",
                allowableValues = {
                        "PHYSICAL_SYMPTOMS", "FETAL_MOVEMENT", "MEDICATION_COMPLIANCE",
                        "MEDICATION_SIDE_EFFECTS", "MOOD_STATUS", "MENTAL_HEALTH",
                        "DAILY_LIFE", "FAMILY_SUPPORT", "PATIENT_CONCERNS"
                }
        )
        EQuestionType type,
        
        @JsonProperty(value = "answer")
        @NotBlank
        @Schema(description = "질문 응답", example = "질문 응답을 작성해주세요.")
        String answer
) {
    public static QuestionDto from(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getType(),
                question.getAnswer()
        );
    }
}