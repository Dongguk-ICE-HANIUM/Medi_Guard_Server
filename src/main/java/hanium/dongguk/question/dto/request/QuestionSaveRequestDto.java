package hanium.dongguk.question.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.question.dto.QuestionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuestionSaveRequestDto(
        @JsonProperty(value = "questionList")
        @NotNull
        @Schema(description = "저장할 질문 목록")
        List<QuestionDto> questionList
) {
}