package hanium.dongguk.calendar.dto.response;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.EEmotion;

public record CalendarResponseDto(
        EEmotion emotion,
        String description
) {
    public static CalendarResponseDto of(Calendar calendar) {
        return new CalendarResponseDto(
                calendar.getEmotion(),
                calendar.getDescription()
        );
    }
}