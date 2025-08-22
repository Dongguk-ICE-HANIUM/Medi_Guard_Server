package hanium.dongguk.drug.patientdrug.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "환자 약물 활성화 상태 변경 요청 DTO")
public record PatchPatientDrugIsEssentialRequestDto(
        @Schema(description = "활성화 상태 (true: 활성화/복용중, false: 비활성화/복용중단)", 
                example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "보류(isActive) 여부가 누락되었습니다.")
        @JsonProperty("isActive")
        Boolean isActive
) {
}
