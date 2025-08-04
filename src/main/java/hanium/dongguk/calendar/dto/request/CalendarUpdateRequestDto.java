package hanium.dongguk.calendar.dto.request;

import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CalendarUpdateRequestDto(
        @NotNull UUID calendarId,
        String description,
        @NotNull EEmotion emotion,
        EQuestionType questionType
) {}