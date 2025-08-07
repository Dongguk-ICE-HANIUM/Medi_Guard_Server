package hanium.dongguk.calendar.dto.response;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;

import java.time.LocalDate;
import java.util.UUID;

public record CalendarResponseDto(
        UUID id,
        LocalDate date,
        String description,
        EEmotion emotion,
        EQuestionType questionType
        //List<DrugInfo> drugs
) {
    public static CalendarResponseDto of(Calendar calendar) {
        return new CalendarResponseDto(
                calendar.getId(),
                calendar.getDate(),
                calendar.getDescription(),
                calendar.getEmotion(),
                calendar.getQuestionType()
        );
    }
}

//patient drug 생성 후 밑에 코드 사용 예정
/**
 {
 public static CalendarResponseDto of(Calendar calendar, List<CalendarDrug> calendarDrugs) {
 List<DrugInfo> drugList = calendarDrugs.stream()
 .map(drug -> new DrugInfo(
 drug.getPatientDrug().getDrug().getName(), // 약 이름
 drug.getPatientDrug().getStartAt(),
 drug.getPatientDrug().getEndAt()
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

 public record DrugInfo(String name, LocalDate startAt, LocalDate endAt) {}
 }
 */