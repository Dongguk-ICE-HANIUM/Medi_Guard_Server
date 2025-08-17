package hanium.dongguk.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.question.domain.EQuestionType;
import hanium.dongguk.question.domain.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record QuestionDto(
        @JsonProperty(value = "questionId")
        @Schema(description = "질문 ID", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        UUID id,
        
        @JsonProperty(value = "questionType")
        @NotNull
        @Schema(description = "질문 유형", example = "MOOD")
        EQuestionType type,
        
        @JsonProperty(value = "questionAnswer")
        @NotBlank
        @Schema(description = "질문 응답", example = "좋음")
        String answer
) {
    public static QuestionDto of(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getType(),
                question.getAnswer()
        );
    }
}