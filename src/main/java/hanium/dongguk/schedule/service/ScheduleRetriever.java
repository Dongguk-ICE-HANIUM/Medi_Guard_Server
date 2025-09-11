package hanium.dongguk.schedule.service;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.schedule.domain.EScheduleStatus;
import hanium.dongguk.schedule.domain.Schedule;
import hanium.dongguk.schedule.domain.ScheduleRepository;
import hanium.dongguk.schedule.exception.ScheduleErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleRetriever {

    private final ScheduleRepository scheduleRepository;

    public boolean existsByScheduleTime(UUID userId, LocalDateTime scheduleTime) {
        LocalDateTime startTime = scheduleTime.minusHours(1);
        LocalDateTime endTime = scheduleTime.plusHours(1);
        return scheduleRepository.existsByPatientIdAndScheduleTimeBetween(userId, startTime, endTime);
    }

    public Optional<Schedule> getRecentSchedule(UUID userId){
        LocalDateTime now = LocalDateTime.now();
        return scheduleRepository
                .findTopByPatientIdAndStatusAndScheduleTimeGreaterThanEqualOrderByScheduleTimeAsc(userId,
                                                                                                  EScheduleStatus.WAITING,
                                                                                                  now);
    }

    public Page<Schedule> getCompletedScheduleList(UUID userId, Pageable pageable) {
        return scheduleRepository.findByPatientIdAndStatusOrderByScheduleTimeDesc(userId,
                                                                                  EScheduleStatus.COMPLETED,
                                                                                  pageable);
    }

    public Schedule getSchedule(UUID userId, UUID scheduleId) {
        return scheduleRepository.findByPatientIdAndId(userId, scheduleId)
                .orElseThrow(() -> CommonException.type(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
    }
}
