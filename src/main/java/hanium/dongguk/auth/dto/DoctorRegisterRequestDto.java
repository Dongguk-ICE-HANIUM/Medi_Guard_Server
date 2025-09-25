package hanium.dongguk.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.user.core.domain.Email;
import hanium.dongguk.user.doctor.domain.EDepartment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DoctorRegisterRequestDto(

        @NotNull
        @Schema(example = "dhzktldh@naver.com")
        @JsonProperty("email")
        Email email,

        @NotBlank
        @Schema(example = "tkddbs3535")
        @JsonProperty("password")
        String password,

        @NotBlank
        @Schema(example = "추상윤")
        @JsonProperty("name")
        String name,

        @NotBlank
        @Schema(example = "동국대병원")
        @JsonProperty("hospitalName")
        String hospitalName,

        @NotNull
        @Schema(example = "INTERNAL_MEDICINE", implementation = EDepartment.class)
        @JsonProperty("department")
        EDepartment department

) {
}
