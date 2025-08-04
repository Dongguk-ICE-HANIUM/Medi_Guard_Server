package hanium.dongguk.question.dto.request;

import hanium.dongguk.question.domain.EQuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record QuestionUpdateRequestDto(
        @NotNull List<QuestionUpdateItemDto> questionList
) {
    public record QuestionUpdateItemDto(
            @NotNull UUID id,
            @NotNull EQuestionType type,
            @NotBlank String answer
    ) {}
}
