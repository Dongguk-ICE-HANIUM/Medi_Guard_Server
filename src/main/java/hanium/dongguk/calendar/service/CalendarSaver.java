package hanium.dongguk.calendar.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalendarSaver {

    private final CalendarRepository calendarRepository;

    public Calendar save(Calendar calendar) {
        return calendarRepository.save(calendar);
    }
}