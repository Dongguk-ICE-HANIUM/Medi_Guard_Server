package hanium.dongguk.calendar.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.CalendarRepository;
import hanium.dongguk.calendar.dto.request.CalendarSaveRequestDto;
import hanium.dongguk.calendar.dto.request.CalendarUpdateRequestDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.global.exception.CalendarErrorCode;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.patient.UserPatient;
import hanium.dongguk.user.patient.UserPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserPatientRepository userPatientRepository;

    /**
     * 오늘의 기분 저장
     */
    @Transactional
    public CalendarResponseDto saveCalendar(UUID patientId, CalendarSaveRequestDto requestDto) {
        UserPatient userPatient = userPatientRepository.findById(patientId)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS));

        Calendar calendar = Calendar.create(
                requestDto.date(),
                requestDto.description(),
                requestDto.emotion(),
                requestDto.questionType(), // 추가됨
                userPatient
        );

        calendarRepository.save(calendar);

        return CalendarResponseDto.of(calendar);
    }

    /**
     * 오늘의 기분 수정
     */
    @Transactional
    public CalendarResponseDto updateCalendar(UUID patientId, CalendarUpdateRequestDto requestDto) {
        UserPatient userPatient = userPatientRepository.findById(patientId)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS));

        Calendar calendar = calendarRepository.findById(requestDto.calendarId())
                .orElseThrow(() -> new CommonException(CalendarErrorCode.CALENDAR_NOT_FOUND));

        if (!calendar.getUserPatient().equals(userPatient)) {
            throw new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }

        // Calendar 엔티티에 updateEmotion 메서드 사용
        calendar.updateEmotion(requestDto.emotion(), requestDto.description());

        return CalendarResponseDto.of(calendar);
    }

    /**
     * 오늘의 기분 조회
     */
    @Transactional(readOnly = true)
    public CalendarResponseDto getTodayMood(UUID patientId, LocalDate date) {
        UserPatient userPatient = userPatientRepository.findById(patientId)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS));

        Calendar calendar = calendarRepository.findByDateAndUserPatient(date, userPatient)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.CALENDAR_NOT_FOUND));

        return CalendarResponseDto.of(calendar);
    }
}
//patient drug 생성 후 수정 예정