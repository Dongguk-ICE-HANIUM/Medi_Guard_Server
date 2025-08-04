package hanium.dongguk.question.dto.request;

import hanium.dongguk.question.domain.EQuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuestionSaveRequestDto(
        @NotNull List<QuestionItemDto> questionList
) {
    public record QuestionItemDto(
            @NotNull EQuestionType type,
            @NotBlank String answer
    ) {}
}