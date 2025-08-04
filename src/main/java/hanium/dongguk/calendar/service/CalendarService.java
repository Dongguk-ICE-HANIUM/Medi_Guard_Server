package hanium.dongguk.calendar.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import hanium.dongguk.calendar.dto.request.CalendarSaveRequestDto;
import hanium.dongguk.calendar.dto.request.CalendarUpdateRequestDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.global.exception.GlobalErrorCode;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    @Transactional(readOnly = true)
    public CalendarResponseDto getTodayMood(LocalDate date) {
        Calendar calendar = calendarRepository.findByDate(date)
                .orElseThrow(() -> new CommonException(GlobalErrorCode.NOT_FOUND));
        return CalendarResponseDto.of(calendar);
    }

    @Transactional
    public CalendarResponseDto saveMood(CalendarSaveRequestDto requestDto) {
        Calendar calendar = Calendar.create(
                requestDto.date(),
                requestDto.description(),
                requestDto.emotion(),
                requestDto.questionType()
        );
        return CalendarResponseDto.of(calendarRepository.save(calendar));
    }

    @Transactional
    public CalendarResponseDto updateMood(CalendarUpdateRequestDto requestDto) {
        Calendar calendar = calendarRepository.findById(requestDto.calendarId())
                .orElseThrow(() -> new CommonException(GlobalErrorCode.NOT_FOUND));

        calendar.updateEmotion(requestDto.emotion(), requestDto.description());
        return CalendarResponseDto.of(calendar);
    }
}