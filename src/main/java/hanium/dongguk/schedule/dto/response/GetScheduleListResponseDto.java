package hanium.dongguk.schedule.dto.response;

import java.util.List;

public record GetScheduleListResponseDto(
        List<ScheduleResponseDto> scheduleList
) {
}
