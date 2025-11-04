package hanium.dongguk.schedule.service;

import hanium.dongguk.schedule.domain.Schedule;
import hanium.dongguk.schedule.domain.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ScheduleRetriever {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> findAllByUserIdAndScheduleTimeBetween(
            final UUID userId,
            final LocalDateTime startDateTime,
            final LocalDateTime endDateTime
    ) {
        return scheduleRepository.findByPatientIdAndScheduleTimeBetween(
                userId,
                startDateTime,
                endDateTime
        );
    }
}
