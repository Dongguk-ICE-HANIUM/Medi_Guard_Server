package hanium.dongguk.drug.patientdrug.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.drug.patientdrug.domain.ETakingType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreatePatientDrugRequestDto(
        @NotNull(message = "약물 ID가 누락되었습니다.")
        @JsonProperty("drugId")
        UUID drugId,

        @NotBlank(message = "약물 이름이 누락되었거나 비어있습니다.")
        @JsonProperty("name")
        String name,

        @NotNull(message = "복용 시작 날짜가 누락되었습니다.")
        @JsonProperty("startAt")
        LocalDate startAt,

        @NotNull(message = "복용 종료 날짜가 누락되었습니다.")
        @JsonProperty("endAt")
        LocalDate endAt,

        @NotNull(message = "복약 주기가 누락되었습니다.")
        @JsonProperty("takingType")
        ETakingType takingType,

        @NotNull(message = "interval이 누락되었습니다.")
        @Min(0)
        @JsonProperty("interval")
        Short takingInterval,

        @NotNull(message = "특정일 리스트가 누락되었습니다.")
        @JsonProperty("specificDateList")
        List<LocalDate> requestSpecificDateList,

        @Min(0)
        @JsonProperty("perDay")
        Short perDay,

        @NotNull(message = "1회 복용량이 누락되었습니다.")
        @DecimalMin(value = "0.0", inclusive = false)
        @JsonProperty("amount")
        BigDecimal amount,

        @JsonProperty("groupId")
        UUID groupId
) {
        @AssertTrue(message = "유효하지 않은 복용 기간입니다.")
        public boolean isStartBeforeOrEqualToEnd() {
                if (startAt == null || endAt == null) return true;
                return !startAt.isAfter(endAt);
        }
}
