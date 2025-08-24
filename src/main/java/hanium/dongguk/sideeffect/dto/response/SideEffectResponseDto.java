package hanium.dongguk.sideeffect.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.sideeffect.domain.SideEffect;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "부작용 정보 DTO")
public record SideEffectResponseDto(
        @JsonProperty("id")
        @Schema(description = "부작용 ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        
        @JsonProperty("drug_name")
        @Schema(description = "약물명", example = "약물1")
        String drugName,
        
        @JsonProperty("description")
        @Schema(description = "부작용 설명", example = "두통이 있고 어지럽다.")
        String description
) {
    public static SideEffectResponseDto from(SideEffect sideEffect) {
        return new SideEffectResponseDto(
                sideEffect.getId(),
                sideEffect.getCalendarDrug().getPatientDrug().getName(),
                sideEffect.getDescription()
        );
    }
}