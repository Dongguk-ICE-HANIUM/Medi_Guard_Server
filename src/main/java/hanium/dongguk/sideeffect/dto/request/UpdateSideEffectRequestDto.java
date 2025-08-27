package hanium.dongguk.sideeffect.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "부작용 수정 요청 DTO")
public record UpdateSideEffectRequestDto(
        @JsonProperty("description")
        @NotBlank(message = "설명은 필수입니다.")
        @Schema(description = "부작용 설명", example = "두통이 있고 어지럽다.")
        String description
) {
}
