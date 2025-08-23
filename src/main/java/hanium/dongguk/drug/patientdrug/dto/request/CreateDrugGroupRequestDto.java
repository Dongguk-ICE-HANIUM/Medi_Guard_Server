package hanium.dongguk.drug.patientdrug.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CreateDrugGroupRequestDto(
        @NotBlank(message = "그룹 이름이 비어있거나 누락되었습니다.")
        @JsonProperty("name")
        String name
) {
}
