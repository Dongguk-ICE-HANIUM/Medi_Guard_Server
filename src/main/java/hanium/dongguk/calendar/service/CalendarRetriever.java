package hanium.dongguk.calendar.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.CalendarRepository;
import hanium.dongguk.user.patient.domain.UserPatient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CalendarRetriever {

    private final CalendarRepository calendarRepository;

    public Optional<Calendar> findByDateAndUserPatient(LocalDate date, UserPatient userPatient) {
        return calendarRepository.findByDateAndUserPatient(date, userPatient);
    }
}