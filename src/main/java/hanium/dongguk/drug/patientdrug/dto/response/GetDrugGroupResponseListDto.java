package hanium.dongguk.drug.patientdrug.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record GetDrugGroupResponseListDto(
        @JsonProperty("drugGroupList")
       List<GetDrugGroupResponseDto> getDrugGroupResponseDtoList
) {
    public static GetDrugGroupResponseListDto of(
            final List<GetDrugGroupResponseDto> getDrugGroupResponseDtoList) {
        return GetDrugGroupResponseListDto.builder()
                .getDrugGroupResponseDtoList(getDrugGroupResponseDtoList)
                .build();
    }
}
