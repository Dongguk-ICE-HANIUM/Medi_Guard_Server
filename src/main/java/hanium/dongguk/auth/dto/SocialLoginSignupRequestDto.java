package hanium.dongguk.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record SocialLoginSignupRequestDto(

        @NotNull
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
        @JsonProperty("userId")
        UUID userId,

        @NotBlank
        @Schema(example = "홍길동")
        @JsonProperty("name")
        String name,

        @NotBlank
        @Schema(example = "2003-03-03")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다")
        @JsonProperty("birthday")
        String birthday,

        @NotNull
        @Min(100) @Max(220)
        @Schema(example = "180")
        @JsonProperty("height")
        Integer height,

        @NotNull
        @Min(35) @Max(150)
        @Schema(example = "80")
        @JsonProperty("weight")
        Integer weight,

        @NotBlank
        @Schema(example = "2025-08-24")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다")
        @JsonProperty("dueDate")
        String dueDate,

        @NotNull
        @Max(50)
        @Schema(example = "24")
        @JsonProperty("pregnancyWeek")
        Integer pregnancyWeek,

        @NotNull
        @Schema(example = "false")
        @JsonProperty("feeding")
        boolean feeding

        // List<String> allergyList,

        // List<String> diseaseList
) {
}
