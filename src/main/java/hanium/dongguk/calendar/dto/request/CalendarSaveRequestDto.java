package hanium.dongguk.calendar.dto.request;

import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CalendarSaveRequestDto(
        @NotNull
        LocalDate date,
        
        @NotBlank
        String description,
        
        @NotNull
        EEmotion emotion,
        
        @NotNull
        EQuestionType questionType
) {
}