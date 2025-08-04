package hanium.dongguk.calendar.dto.request;

import hanium.dongguk.calendar.domain.EEmotion;

import java.util.UUID;

public record CalendarUpdateRequestDto(
        UUID calendarId,
        String description,
        EEmotion emotion
) {}