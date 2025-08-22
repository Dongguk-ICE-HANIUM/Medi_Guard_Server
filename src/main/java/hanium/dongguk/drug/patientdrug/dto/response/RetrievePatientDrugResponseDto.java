package hanium.dongguk.drug.patientdrug.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.drug.drug.domain.Drug;
import hanium.dongguk.drug.patientdrug.domain.DrugGroup;
import hanium.dongguk.drug.patientdrug.domain.ETakingType;
import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.drug.patientdrug.domain.TakingType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record RetrievePatientDrugResponseDto(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        String name,

        @JsonProperty("code")
        String code,

        @JsonProperty("effect")
        String effect,

        @JsonProperty("warning")
        String warning,

        @JsonProperty("sideEffect")
        String sideEffect,

        @JsonProperty("interaction")
        String interaction,

        @JsonProperty("depositMethod")
        String depositMethod,

        @JsonProperty("startAt")
        LocalDate startAt,

        @JsonProperty("endAt")
        LocalDate endAt,

        @JsonProperty("takingType")
        ETakingType takingType,

        @JsonProperty("perDay")
        Short perDay,

        @JsonProperty("amount")
        BigDecimal amount,

        @JsonProperty("isActive")
        Boolean isActive,

        @JsonProperty("isEssential")
        Boolean isEssential,

        @JsonProperty("groupName")
        String groupName,

        @JsonProperty("groupId")
        UUID groupId,

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
