package hanium.dongguk.user.core.dto.request;

import hanium.dongguk.user.core.domain.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NormalRegisterRequestDto(

        @NotNull
        @Schema(example = "wpsxkehf7@naver.com")
        Email email,

        @NotBlank
        @Schema(example = "tkddbs3535")
        String password,

        @NotBlank
        @Schema(example = "홍길동")
        String name,

        @NotBlank
        @Schema(example = "2003-03-03")
        String birthday,

        @NotNull
        @Min(100) @Max(220)
        @Schema(example = "180")
        Integer height,

        @NotNull
        @Min(35) @Max(150)
        @Schema(example = "80")
        Integer weight,

        @NotBlank
        @Schema(example = "2025-08-24")
        String dueDate,

        @NotNull
        @Max(50)
        @Schema(example = "24")
        Integer pregnancyWeek,

        @NotNull
        @Schema(example = "false")
        boolean feeding

        // List<String> allergyList,

       // List<String> diseaseList

) {

}
