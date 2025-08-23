package hanium.dongguk.drug.patientdrug.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.drug.patientdrug.domain.DrugGroup;
import lombok.Builder;

import java.util.UUID;

@Builder
public record GetDrugGroupResponseDto(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        String name
) {
    public static GetDrugGroupResponseDto from(
            final DrugGroup drugGroup) {
        return GetDrugGroupResponseDto.builder()
                .id(drugGroup.getId())
                .name(drugGroup.getName())
                .build();
    }
}
