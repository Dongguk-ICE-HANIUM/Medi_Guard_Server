package hanium.dongguk.calendar.service.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.CalendarDrug;
import hanium.dongguk.calendar.dto.request.SaveCalendarRequestDto;
import hanium.dongguk.calendar.dto.request.UpdateCalendarRequestDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.calendar.dto.response.MonthCalendarResponseDto;
import hanium.dongguk.calendar.dto.response.MonthCalendarResponseListDto;
import hanium.dongguk.calendar.exception.CalendarErrorCode;
import hanium.dongguk.calendar.service.retriever.CalendarDrugRetriever;
import hanium.dongguk.calendar.service.retriever.CalendarRetriever;
import hanium.dongguk.calendar.service.saver.CalendarSaver;
import hanium.dongguk.calendar.validator.CalendarValidator;
import hanium.dongguk.drug.patientdrug.domain.ETakingType;
import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.drug.patientdrug.domain.TakingType;
import hanium.dongguk.drug.patientdrug.service.retriever.PatientDrugRetriever;
import hanium.dongguk.drug.patientdrug.service.service.ParticularDateRetriever;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.schedule.domain.Schedule;
import hanium.dongguk.schedule.service.ScheduleRetriever;
import hanium.dongguk.sideeffect.service.SideEffectRetriever;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRetriever calendarRetriever;
    private final CalendarSaver calendarSaver;
    private final UserPatientRetriever userPatientRetriever;
    private final CalendarValidator calendarValidator;
    private final CalendarDrugRetriever calendarDrugRetriever;
    private final SideEffectRetriever sideEffectRetriever;
    private final PatientDrugRetriever patientDrugRetriever;
    private final ScheduleRetriever scheduleRetriever;
    private final ParticularDateRetriever particularDateRetriever;

    @Transactional(readOnly = true)
    public CalendarResponseDto getCalendarByDate(UUID patientId, LocalDate date) {
        Optional<Calendar> calendarOpt = calendarRetriever.findByDateAndUserPatient(date, patientId);

        return calendarOpt.map(CalendarResponseDto::from).orElseGet(CalendarResponseDto::empty);

    }

    @Transactional
    public void saveCalendar(UUID patientId, SaveCalendarRequestDto requestDto) {
        duplicateValidate(patientId);

        UserPatient userPatient = userPatientRetriever.getUserPatient(patientId);

        Calendar calendar = Calendar.create(
                LocalDate.now(),
                requestDto.description(),
                requestDto.emotion(),
                userPatient
        );

        calendarSaver.save(calendar);
    }

    @Transactional
    public void updateCalendar(UUID patientId, UpdateCalendarRequestDto requestDto) {

        Calendar calendar = calendarRetriever.getTodayCalendar(patientId)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        calendar.updateEmotion(requestDto.emotion(), requestDto.description());
    }

    private void duplicateValidate(UUID patientId) {
        calendarRetriever.findByDateAndUserPatient(LocalDate.now(), patientId)
                .ifPresent((calendar) -> {
                    throw CommonException.type(CalendarErrorCode.CALENDAR_ALREADY_EXISTS);
                });
    }

    public MonthCalendarResponseListDto getMonthCalendar(
            UUID userId,
            LocalDate targetDate) {
        YearMonth yearMonth = YearMonth.from(targetDate);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        // targetDate에 해당하는 월의 정보(recordDate)를 들고 있는 CalenderDrug를 긁어온다.
        List<CalendarDrug> calendarDrugs = calendarDrugRetriever
                .findAllByUserPatientIdAndRecordDateBetween(userId, start, end);

        // recordDate를 중심으로 묶어서 관리한다.
        Map<LocalDate, List<CalendarDrug>> calendarDrugsByDate
                = calendarDrugs.stream()
                               .collect(Collectors.groupingBy(CalendarDrug::getRecordDate));

        Set<UUID> calendarDrugIds
                = calendarDrugs.stream().map(CalendarDrug::getId)
                                        .collect(Collectors.toSet());

        // SideEffect가 있는 약물의 Set을 만든다.
        Set<UUID> sideEffectDrugIdSet
                = new HashSet<>(
                        sideEffectRetriever.findCalendarDrugIdsWithSideEffects(calendarDrugIds)
                );

        // 3. 복약 예정 날짜
        List<PatientDrug> patientDrugList = patientDrugRetriever.findAllByUserId(userId);
        Set<LocalDate> scheduledDates = new HashSet<>();
        for(PatientDrug targetPatientDrug : patientDrugList) {
            TakingType targetTakingType = targetPatientDrug.getTakingType();
            if(targetTakingType != null && targetTakingType.getType() != null
                    && targetTakingType.getTakingInterval() != null) {
                scheduledDates.addAll(
                        calculateScheduledDates(
                                targetPatientDrug.getStartAt(),
                                yearMonth,
                                targetTakingType
                        )
                );
            }
        }

        // 4. 진료 일정
        List<Schedule> schedules
                = scheduleRetriever.findAllByUserIdAndScheduleTimeBetween(
                        userId,
                        start.atStartOfDay(),
                        end.atTime(LocalTime.MAX)
                );
        Set<LocalDate> scheduleDates = schedules.stream()
                .map(s -> s.getScheduleTime().toLocalDate())
                .collect(Collectors.toSet());

        // 5. 날짜별 응답 생성
        List<MonthCalendarResponseDto> result = new ArrayList<>();
        for(int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);

            List<CalendarDrug> drugList = calendarDrugsByDate.getOrDefault(date, List.of());
            boolean didTakePill = !drugList.isEmpty();
            boolean hasSideEffect = drugList.stream()
                    .anyMatch(d -> sideEffectDrugIdSet.contains(d.getId()));
            boolean isTakeScheduled = scheduledDates.contains(date);
            boolean isScheduled = scheduleDates.contains(date);

            result.add(MonthCalendarResponseDto.of(
                    didTakePill,
                    hasSideEffect,
                    isTakeScheduled,
                    isScheduled
            ));
        }

        return MonthCalendarResponseListDto.of(result);
    }

    private List<LocalDate> calculateScheduledDates(
            LocalDate startAt,
            YearMonth month,
            TakingType takingType) {
        Set<LocalDate> result = new HashSet<>();
        if(takingType.getType() == ETakingType.EVERY_DAY) {
            for(int i = 1; i <= month.lengthOfMonth(); i++) {
                result.add(month.atDay(i));
            }
        } else if(takingType.getType() == ETakingType.PARTICULAR_INTERVAL) {
            long interval = takingType.getTakingInterval();
            LocalDate current = startAt;
            while(!current.isAfter(month.atEndOfMonth())) {
                if(!current.isBefore(month.atDay(1))) {
                    result.add(current);
                }
                current = current.plusDays(interval);
            }
        } else if(takingType.getType() == ETakingType.PARTICULAR_DAY) {
            int flag = takingType.getTakingInterval();

            LocalDate current = month.atDay(1);
            while (!current.isAfter(month.atEndOfMonth())) {
                DayOfWeek dayOfWeek = current.getDayOfWeek(); // 월요일 ~ 일요일
                /*
                 *   월  화   수  목   금  토   일
                 * 2^6 2^5 2^4 2^3 2^2 2^1 2^0
                 *   1   0   0   0   0   0   0 => 월요일
                 */
                int dayBit = 1 << (7 - dayOfWeek.getValue());

                if ((flag & dayBit) != 0) {
                    result.add(current);
                }
                current = current.plusDays(1);
            }
        } else if(ETakingType.SPECIFIC_DATE == takingType.getType()) {
            List<LocalDate> dateList
                    = particularDateRetriever.getParticularDateList(takingType.getId());
            result.addAll(dateList);
        } else {
            return List.of();
        }

        return List.copyOf(result);
    }
}