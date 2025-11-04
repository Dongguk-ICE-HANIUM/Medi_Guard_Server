package hanium.dongguk.calendar.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record MonthCalendarResponseListDto(
        @JsonProperty("dateList")
        List<MonthCalendarResponseDto> monthCalendarResponseDtoList
) {
    public static MonthCalendarResponseListDto of(
            final List<MonthCalendarResponseDto> monthCalendarResponseDtoList) {
        return MonthCalendarResponseListDto.builder()
                .monthCalendarResponseDtoList(monthCalendarResponseDtoList)
                .build();
    }
}
