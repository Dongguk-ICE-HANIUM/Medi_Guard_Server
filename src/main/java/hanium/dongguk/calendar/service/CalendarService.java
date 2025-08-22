package hanium.dongguk.calendar.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.dto.request.SaveCalendarRequestDto;
import hanium.dongguk.calendar.dto.request.UpdateCalendarRequestDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.calendar.exception.CalendarErrorCode;
import hanium.dongguk.calendar.validator.CalendarValidator;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRetriever calendarRetriever;
    private final CalendarSaver calendarSaver;
    private final UserPatientRetriever userPatientRetriever;
    private final CalendarValidator calendarValidator;

    @Transactional(readOnly = true)
    public CalendarResponseDto getCalendarByDate(UUID patientId, LocalDate date) {
        Calendar calendar = calendarRetriever.findByDateAndUserPatient(date, patientId)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        return CalendarResponseDto.from(calendar);
    }

    @Transactional
    public void saveCalendar(UUID patientId, SaveCalendarRequestDto requestDto) {
        calendarValidator.validateFutureDate(requestDto.date());
        duplicateValidate(patientId, requestDto.date());

        UserPatient userPatient = userPatientRetriever.getUserPatient(patientId);

        Calendar calendar = Calendar.create(
                requestDto.date(),
                requestDto.description(),
                requestDto.emotion(),
                userPatient
        );

        calendarSaver.save(calendar);
    }

    @Transactional
    public void updateCalendar(UUID patientId, UUID calendarId, UpdateCalendarRequestDto requestDto) {

        Calendar calendar = calendarRetriever.findByIdAndUserPatient(calendarId, patientId)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        calendar.updateEmotion(requestDto.emotion(), requestDto.description());
    }

    private void duplicateValidate(UUID patientId, LocalDate date) {
        calendarRetriever.findByDateAndUserPatient(date, patientId)
                .ifPresent((calendar) -> {
                    throw CommonException.type(CalendarErrorCode.CALENDAR_ALREADY_EXISTS);
                });
    }
}