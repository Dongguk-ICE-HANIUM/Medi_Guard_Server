package hanium.dongguk.schedule.service;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.schedule.domain.Schedule;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.exception.ScheduleErrorCode;
import hanium.dongguk.schedule.validator.ScheduleValidator;
import hanium.dongguk.user.core.service.UserRetriever;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleSaver scheduleSaver;
    private final ScheduleValidator scheduleValidator;
    private final UserPatientRetriever userPatientRetriever;
    private final ScheduleRetriever scheduleRetriever;

    @Transactional
    public void saveSchedule(SaveScheduleRequestDto request, UUID userId) {

        LocalDateTime scheduleTime = request.scheduleTime();

        scheduleValidator.validateScheduleTime(scheduleTime);

        if(!scheduleRetriever.existsByScheduleTime(scheduleTime)) {
            throw CommonException.type(ScheduleErrorCode.DUPLICATE_SCHEDULE_TIME);
        }

        UserPatient userPatient = userPatientRetriever.getUserPatient(userId);

        Schedule schedule = Schedule.create(scheduleTime, userPatient);

        scheduleSaver.save(schedule);
    }
}
