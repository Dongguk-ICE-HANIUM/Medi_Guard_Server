package hanium.dongguk.calendar.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.dto.request.CalendarSaveRequestDto;
import hanium.dongguk.calendar.dto.request.CalendarUpdateRequestDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.calendar.exception.CalendarErrorCode;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.core.domain.User;
import hanium.dongguk.user.core.domain.UserRepository;
import hanium.dongguk.user.core.exception.UserErrorCode;
import hanium.dongguk.user.patient.domain.UserPatient;
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
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CalendarResponseDto getCalendarByDate(UUID patientId, LocalDate date) {
        User user = userRepository.findById(patientId)
                .orElseThrow(() -> CommonException.type(UserErrorCode.NOT_FOUND_USER));
        
        if (!(user instanceof UserPatient userPatient)) {
            throw CommonException.type(UserErrorCode.NOT_FOUND_USER);
        }

        Calendar calendar = calendarRetriever.findByDateAndUserPatient(date, userPatient)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        return CalendarResponseDto.of(calendar);
    }

    @Transactional
    public void saveCalendar(UUID patientId, CalendarSaveRequestDto requestDto) {
        // 미래 날짜 검증
        if (requestDto.date().isAfter(LocalDate.now())) {
            throw CommonException.type(CalendarErrorCode.FUTURE_DATE_NOT_ALLOWED);
        }
        
        User user = userRepository.findById(patientId)
                .orElseThrow(() -> CommonException.type(UserErrorCode.NOT_FOUND_USER));
        
        if (!(user instanceof UserPatient userPatient)) {
            throw CommonException.type(UserErrorCode.NOT_FOUND_USER);
        }

        boolean exists = calendarRetriever.findByDateAndUserPatient(requestDto.date(), userPatient).isPresent();
        if (exists) {
            throw CommonException.type(CalendarErrorCode.CALENDAR_ALREADY_EXISTS);
        }

        Calendar calendar = Calendar.create(
                requestDto.date(),
                requestDto.description(),
                requestDto.emotion(),
                requestDto.questionType(),
                userPatient
        );

        calendarSaver.save(calendar);
    }

    @Transactional
    public void updateCalendar(UUID patientId, UUID calendarId, CalendarUpdateRequestDto requestDto) {
        User user = userRepository.findById(patientId)
                .orElseThrow(() -> CommonException.type(UserErrorCode.NOT_FOUND_USER));
        
        if (!(user instanceof UserPatient userPatient)) {
            throw CommonException.type(UserErrorCode.NOT_FOUND_USER);
        }

        Calendar calendar = calendarRetriever.findByIdAndUserPatient(calendarId, userPatient)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        calendar.updateEmotion(requestDto.emotion(), requestDto.description());
    }
}