package hanium.dongguk.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetTodayScheduleResponseDto(

        @JsonProperty("schedule")
        ScheduleResponseDto schedule,

        @JsonProperty("isToday")
        boolean isToday,

        @JsonProperty("isEmpty")
        boolean isEmpty
) {
        public static GetTodayScheduleResponseDto empty(){
                return new GetTodayScheduleResponseDto(null,false,true);
        }

        public static GetTodayScheduleResponseDto of(ScheduleResponseDto schedule, boolean isToday){
                return new GetTodayScheduleResponseDto(schedule,isToday,false);
        }
}
