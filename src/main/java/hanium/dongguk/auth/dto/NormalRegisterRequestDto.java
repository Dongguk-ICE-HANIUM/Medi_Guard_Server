package hanium.dongguk.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.user.core.domain.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;

public record NormalRegisterRequestDto(

        @NotNull
        @Schema(example = "wpsxkehf7@naver.com")
        @JsonProperty("email")
        Email email,

        @NotBlank
        @Schema(example = "tkddbs3535")
        @JsonProperty("password")
        String password,

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
        @JsonProperty("due_date")
        String dueDate,

        @NotNull
        @Max(50)
        @Schema(example = "24")
        @JsonProperty("pregnancy_week")
        Integer pregnancyWeek,

        @NotNull
        @Schema(example = "false")
        @JsonProperty("feeding")
        boolean feeding

        // List<String> allergyList,

       // List<String> diseaseList

) {

}
