package hanium.dongguk.calendar.dto.request;

import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CalendarUpdateRequestDto(
        @NotBlank
        String description,
        
        @NotNull
        EEmotion emotion,
        
        @NotNull
        EQuestionType questionType
) {
}