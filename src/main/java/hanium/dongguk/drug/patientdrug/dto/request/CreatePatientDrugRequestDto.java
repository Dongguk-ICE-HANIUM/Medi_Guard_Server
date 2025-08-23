package hanium.dongguk.drug.patientdrug.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.drug.patientdrug.domain.ETakingType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(description = "환자 약물 등록 요청 DTO")
public record CreatePatientDrugRequestDto(
        @Schema(description = "약물 ID", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "약물 ID가 누락되었습니다.")
        @JsonProperty("drugId")
        UUID drugId,

        @Schema(description = "약물 이름", example = "타이레놀", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "약물 이름이 누락되었거나 비어있습니다.")
        @JsonProperty("name")
        String name,

        @Schema(description = "복용 시작 날짜", example = "2025-01-01", requiredMode = Schema.RequiredMode.REQUIRED, format = "date")
        @NotNull(message = "복용 시작 날짜가 누락되었습니다.")
        @JsonProperty("startAt")
        LocalDate startAt,

        @Schema(description = "복용 종료 날짜", example = "2025-01-31", requiredMode = Schema.RequiredMode.REQUIRED, format = "date")
        @NotNull(message = "복용 종료 날짜가 누락되었습니다.")
        @JsonProperty("endAt")
        LocalDate endAt,

        @Schema(description = "복용 타입", example = "EVERY_DAY", requiredMode = Schema.RequiredMode.REQUIRED, 
                allowableValues = {"EVERY_DAY", "PARTICULAR_INTERVAL", "PARTICULAR_DAY", "SPECIFIC_DATE", "NEED"})
        @NotNull(message = "복약 주기가 누락되었습니다.")
        @JsonProperty("takingType")
        ETakingType takingType,

        @Schema(description = "복용 간격 (일/요일 단위, 0 이상)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
        @NotNull(message = "interval이 누락되었습니다.")
        @Min(0)
        @JsonProperty("interval")
        Short takingInterval,

        @Schema(description = "특정 복용 날짜 리스트 (SPECIFIC_DATE 타입 시 필수)", 
                example = "[]", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "특정일 리스트가 누락되었습니다.")
        @JsonProperty("specificDateList")
        List<LocalDate> requestSpecificDateList,

        @Schema(description = "하루 복용 횟수 (0 이상)", example = "3", minimum = "0")
        @Min(0)
        @JsonProperty("perDay")
        Short perDay,

        @Schema(description = "1회 복용량 (mg 단위, 0보다 큰 값)", example = "500.0", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0.01")
        @NotNull(message = "1회 복용량이 누락되었습니다.")
        @DecimalMin(value = "0.0", inclusive = false)
        @JsonProperty("amount")
        BigDecimal amount,

        @Schema(description = "약물 그룹 ID (선택사항)", example = "987f6543-d21c-43b2-9876-543210987654")
        @JsonProperty("groupId")
        UUID groupId
) {
        @Schema(hidden = true)
        @AssertTrue(message = "유효하지 않은 복용 기간입니다.")
        public boolean isStartBeforeOrEqualToEnd() {
                if (startAt == null || endAt == null) return true;
                return !startAt.isAfter(endAt);
        }
}
