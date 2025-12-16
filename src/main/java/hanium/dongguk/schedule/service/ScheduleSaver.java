package hanium.dongguk.schedule.service;

import hanium.dongguk.schedule.domain.Schedule;
import hanium.dongguk.schedule.domain.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleSaver {

    private final ScheduleRepository scheduleRepository;

    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
}
