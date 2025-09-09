package hanium.dongguk.schedule.dto.request;

import java.time.LocalDateTime;

public record SaveScheduleRequestDto(
        LocalDateTime scheduleTime
) {
}
