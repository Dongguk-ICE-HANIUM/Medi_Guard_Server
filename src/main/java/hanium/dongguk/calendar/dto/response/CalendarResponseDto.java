package hanium.dongguk.calendar.dto.response;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.EEmotion;

import java.time.LocalDate;
import java.util.UUID;

public record CalendarResponseDto(
        UUID id,
        LocalDate date,
        String description,
        EEmotion emotion
) {
    public static CalendarResponseDto of(Calendar calendar) {
        return new CalendarResponseDto(
                calendar.getId(),
                calendar.getDate(),
                calendar.getDescription(),
                calendar.getEmotion()
        );
    }
}