package hanium.dongguk.drug.patientdrug.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.notification.taking.domain.NotifiTaking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Schema(description = "환자 약물 복용 알림 정보 DTO")
@Builder
public record RetrievePatientDrugNotifiTakingResponseDto (
        @Schema(description = "알림 ID", example = "111e1111-e11b-11d1-a111-111111111111")
        @JsonProperty("id")
        UUID id,

        @Schema(description = "복용 알림 시간", example = "08:00:00", format = "time")
        @JsonProperty("time")
        LocalTime time
) {
    public static RetrievePatientDrugNotifiTakingResponseDto from(
            final NotifiTaking notifiTaking) {
        return RetrievePatientDrugNotifiTakingResponseDto.builder()
                .id(notifiTaking.getId())
                .time(notifiTaking.getTakingTime())
                .build();
    }
}
