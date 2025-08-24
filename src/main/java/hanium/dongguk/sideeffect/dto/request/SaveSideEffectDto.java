package hanium.dongguk.sideeffect.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SaveSideEffectDto(
        @JsonProperty("id")
        @NotNull(message = "약물 ID는 필수입니다.")
        @Schema(description = "약물 ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        
        @JsonProperty("description")
        @NotBlank(message = "설명은 필수입니다.")
        @Schema(description = "부작용 설명", example = "두통이 있고 어지럽다.")
        String description
) {
}