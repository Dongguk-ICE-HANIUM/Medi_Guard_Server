package hanium.dongguk.drug.patientdrug.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.notification.taking.domain.NotifiTaking;
import lombok.Builder;

import java.time.LocalTime;
import java.util.UUID;

@Builder
public record RetrievePatientDrugNotifiTakingResponseDto (
        @JsonProperty("id")
        UUID id,

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
