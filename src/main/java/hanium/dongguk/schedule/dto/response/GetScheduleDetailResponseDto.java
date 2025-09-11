package hanium.dongguk.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.schedule.domain.Schedule;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetScheduleDetailResponseDto (
        @JsonProperty("scheduleId")
        UUID scheduleId,

        @JsonProperty("doctorName")
        String doctorName,

        @JsonProperty("hospitalName")
        String hospitalName,

        @JsonProperty("dateTime")
        LocalDateTime dateTime,

        @JsonProperty("symptom")
        String symptom,

        @JsonProperty("diagnosis")
        String diagnosis,

        @JsonProperty("guidance")
        String guidance,

        @JsonProperty("warning")
        String warning
){
    public static GetScheduleDetailResponseDto from (Schedule schedule){
        return new GetScheduleDetailResponseDto(schedule.getId(),
                schedule.getDoctor().getName(),
                schedule.getDoctor().getHospitalName(),
                schedule.getScheduleTime(),
                schedule.getSymptom(),
                schedule.getDiagnosis(),
                schedule.getGuidance(),
                schedule.getWarning());
    }
}
