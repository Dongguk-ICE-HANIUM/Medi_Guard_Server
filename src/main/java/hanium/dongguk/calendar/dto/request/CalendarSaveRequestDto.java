package hanium.dongguk.calendar.dto.request;

import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;

import java.time.LocalDate;

public record CalendarSaveRequestDto(
        LocalDate date,
        String description,
        EEmotion emotion,
        EQuestionType questionType
) {}