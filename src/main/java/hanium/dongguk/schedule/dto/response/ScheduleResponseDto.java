package hanium.dongguk.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.schedule.domain.Schedule;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleResponseDto(
        @JsonProperty("scheduleId")
        UUID scheduleId,

        @JsonProperty("doctorName")
        String doctorName,

        @JsonProperty("hospitalName")
        String hospitalName,

        @JsonProperty("time")
        LocalDateTime time
) {

        public static ScheduleResponseDto from(Schedule schedule) {
                return new ScheduleResponseDto(schedule.getId(),
                                               schedule.getDoctor().getName(),
                                               schedule.getDoctor().getHospitalName(),
                                               schedule.getScheduleTime());
        }
}
