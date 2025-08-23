package hanium.dongguk.drug.patientdrug.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.drug.drug.domain.Drug;
import hanium.dongguk.drug.patientdrug.domain.DrugGroup;
import hanium.dongguk.drug.patientdrug.domain.ETakingType;
import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.drug.patientdrug.domain.TakingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(description = "환자 약물 상세 조회 응답 DTO")
@Builder
public record RetrievePatientDrugResponseDto(
        @Schema(description = "환자 약물 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        @JsonProperty("id")
        UUID id,

        @Schema(description = "약물 이름", example = "타이레놀")
        @JsonProperty("name")
        String name,

        @Schema(description = "약물 코드", example = "A123456789")
        @JsonProperty("code")
        String code,

        @Schema(description = "약물 효능", example = "해열, 진통")
        @JsonProperty("effect")
        String effect,

        @Schema(description = "경고사항", example = "간질환 환자 주의")
        @JsonProperty("warning")
        String warning,

        @Schema(description = "부작용", example = "드물게 피부발진, 소화불량")
        @JsonProperty("sideEffect")
        String sideEffect,

        @Schema(description = "상호작용", example = "와파린과 병용 시 출혈 위험 증가")
        @JsonProperty("interaction")
        String interaction,

        @Schema(description = "보관방법", example = "실온보관, 직사광선 피함")
        @JsonProperty("depositMethod")
        String depositMethod,

        @Schema(description = "복용 시작일", example = "2025-01-01", format = "date")
        @JsonProperty("startAt")
        LocalDate startAt,

        @Schema(description = "복용 종료일", example = "2025-01-31", format = "date")
        @JsonProperty("endAt")
        LocalDate endAt,

        @Schema(description = "복용 타입", example = "EVERY_DAY", 
                allowableValues = {"EVERY_DAY", "PARTICULAR_INTERVAL", "PARTICULAR_DAY", "SPECIFIC_DATE", "NEED"})
        @JsonProperty("takingType")
        ETakingType takingType,

        @Schema(description = "하루 복용 횟수", example = "3")
        @JsonProperty("perDay")
        Short perDay,

        @Schema(description = "1회 복용량 (mg)", example = "500.0")
        @JsonProperty("amount")
        BigDecimal amount,

        @Schema(description = "활성화 상태 (복용 중/중단)", example = "true")
        @JsonProperty("isActive")
        Boolean isActive,

        @Schema(description = "필수 약물 여부", example = "false")
        @JsonProperty("isEssential")
        Boolean isEssential,

        @Schema(description = "약물 그룹명", example = "감기약")
        @JsonProperty("groupName")
        String groupName,

        @Schema(description = "약물 그룹 ID", example = "987f6543-d21c-43b2-9876-543210987654")
        @JsonProperty("groupId")
        UUID groupId,

        @Schema(description = "복용 알림 시간 리스트")
        @JsonProperty("notifiTakingList")
        List<RetrievePatientDrugNotifiTakingResponseDto> notifiTakingDtoList
) {
   public static RetrievePatientDrugResponseDto of(
           final PatientDrug patientDrug,
           final Drug drug,
           final DrugGroup drugGroup,
           final TakingType takingType,
           final List<RetrievePatientDrugNotifiTakingResponseDto>
                   notifiTakingDtoList
           ) {
       return RetrievePatientDrugResponseDto.builder()
               .id(patientDrug.getId())
               .name(patientDrug.getName())
               .code(drug.getCode())
               .effect(drug.getEffect())
               .warning(drug.getWarning())
               .sideEffect(drug.getSideEffect())
               .interaction(drug.getInteraction())
               .depositMethod(drug.getDepositMethod())
               .startAt(patientDrug.getStartAt())
               .endAt(patientDrug.getEndAt())
               .takingType(takingType.getType())
               .perDay(patientDrug.getPerDay())
               .amount(patientDrug.getAmount())
               .isActive(patientDrug.getIsActive())
               .isEssential(patientDrug.getIsEssential())
               .groupName(drugGroup != null ? drugGroup.getName() : null)
               .groupId(drugGroup != null ? drugGroup.getId() : null)
               .notifiTakingDtoList(notifiTakingDtoList)
               .build();
   }
}
