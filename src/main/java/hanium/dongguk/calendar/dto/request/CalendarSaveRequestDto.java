package hanium.dongguk.calendar.dto.request;

import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CalendarSaveRequestDto(
        @NotNull LocalDate date,
        String description,
        @NotNull EEmotion emotion,
        @NotNull EQuestionType questionType
) {}
//description 부분은 굳이 not blank가 있을 필요가 없다고 생각해서 null 값 허용하였습니다