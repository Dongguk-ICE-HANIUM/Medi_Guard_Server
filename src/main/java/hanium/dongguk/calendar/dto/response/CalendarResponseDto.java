package hanium.dongguk.calendar.dto.response;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.CalendarDrug;
import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record CalendarResponseDto(
        UUID id,
        LocalDate date,
        String description,
        EEmotion emotion,
        EQuestionType questionType,
        List<DrugInfo> drugs
) {

    public static CalendarResponseDto of(Calendar calendar, List<CalendarDrug> calendarDrugs) {
        List<DrugInfo> drugList = calendarDrugs.stream()
                .map(drug -> new DrugInfo(
                        drug.getPatientDrug().getId(),
                        /* TODO: Drug 엔티티 연동 후 약 이름 불러오기
                         * drug.getPatientDrug().getDrug().getName()
                         */
                        null,  // 임시로 null 값 넣음
                        drug.getPatientDrug().getStartAt(),
                        drug.getPatientDrug().getEndAt(),
                        drug.getTimeSlot()
                ))
                .collect(Collectors.toList());

        return new CalendarResponseDto(
                calendar.getId(),
                calendar.getDate(),
                calendar.getDescription(),
                calendar.getEmotion(),
                calendar.getQuestionType(),
                drugList
        );
    }

    public record DrugInfo(
            UUID patientDrugId,
            String drugName,             // TODO: 약 이름은 Drug 엔티티 연동 후 채울 예정
            LocalDateTime startAt,
            LocalDateTime endAt,
            Integer timeSlot
    ) {}
}