package hanium.dongguk.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record VerifyCodeRequestDto(
        @NotBlank
        @JsonProperty("code")
        @Schema(example = "12345678")
        String code
) {
}
