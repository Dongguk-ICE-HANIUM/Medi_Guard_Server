package hanium.dongguk.question.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.question.domain.EQuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateQuestionDto(

        @JsonProperty(value = "type")
        @NotNull
        @Schema(description = "질문 유형", example = "PHYSICAL_SYMPTOMS")
        EQuestionType type,

        @JsonProperty(value = "answer")
        @NotBlank
        @Schema(description = "질문 응답", example = "질문 응답을 작성해주세요.")
        String answer
) {
}
