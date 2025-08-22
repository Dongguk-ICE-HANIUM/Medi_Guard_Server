package hanium.dongguk.drug.patientdrug.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record PatchPatientDrugIsEssentialRequestDto(
        @NotNull(message = "보류(isEssential) 여부가 누락되었습니다.")
        @JsonProperty("isEssential")
        Boolean isEssential
) {
}
