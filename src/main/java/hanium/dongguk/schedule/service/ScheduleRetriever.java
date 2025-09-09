package hanium.dongguk.schedule.service;

import hanium.dongguk.schedule.domain.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleRetriever {

    private final ScheduleRepository scheduleRepository;

    public boolean existsByScheduleTime(LocalDateTime scheduleTime) {
        LocalDateTime endTime = scheduleTime.plusHours(1);
        return scheduleRepository.existsByScheduleTimeBetween(endTime, LocalDateTime.now());
    }
}
