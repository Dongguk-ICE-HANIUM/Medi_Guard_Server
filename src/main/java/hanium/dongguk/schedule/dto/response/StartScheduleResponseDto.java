package hanium.dongguk.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StartScheduleResponseDto(
        @JsonProperty("code")
        String code
) {
    public static StartScheduleResponseDto from (String code){
        return new StartScheduleResponseDto(code);
    }
}
