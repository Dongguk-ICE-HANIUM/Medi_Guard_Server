package hanium.dongguk.calendar.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MonthCalendarResponseDto(
        @JsonProperty("didTakePill")
        Boolean didTakePill,

        @JsonProperty("hasSideEffect")
        Boolean hasSideEffect,

        @JsonProperty("isTakeScheduled")
        Boolean isTakeScheduled,

        @JsonProperty("isScheduled")
        Boolean isScheduled
) {
    public static MonthCalendarResponseDto of(
            final boolean didTakePill,
            final boolean hasSideEffect,
            final boolean isTakeScheduled,
            final boolean isScheduled
    ) {
        return MonthCalendarResponseDto.builder()
                .didTakePill(didTakePill)
                .hasSideEffect(hasSideEffect)
                .isTakeScheduled(isTakeScheduled)
                .isScheduled(isScheduled)
                .build();
    }
}