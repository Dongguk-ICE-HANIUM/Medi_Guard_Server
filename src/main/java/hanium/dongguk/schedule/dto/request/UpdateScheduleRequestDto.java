package hanium.dongguk.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateScheduleRequestDto(
        @JsonProperty("symptom")
        @NotBlank
        @Schema(example = "복통이 있음, 어지러움 동반")
        String symptom,

        @JsonProperty("diagnosis")
        @NotBlank
        @Schema(example = "태아성장 상태 정상, 철분제 복용 시작 권장")
        String diagnosis,

        @JsonProperty("guidance")
        @NotBlank
        @Schema(example = "철분제는 다음주부터 하루 1회 복용")
        String guidance,

        @JsonProperty("warning")
        @NotBlank
        @Schema(example = "무리가 되는 운동 금지")
        String warning
) {
}
