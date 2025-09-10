package hanium.dongguk.schedule.service;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.schedule.domain.Schedule;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.dto.response.GetTodayScheduleResponseDto;
import hanium.dongguk.schedule.dto.response.ScheduleResponseDto;
import hanium.dongguk.schedule.exception.ScheduleErrorCode;
import hanium.dongguk.schedule.validator.ScheduleValidator;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
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

        if(!scheduleRetriever.existsByScheduleTime(userId, scheduleTime)) {
            throw CommonException.type(ScheduleErrorCode.DUPLICATE_SCHEDULE_TIME);
        }

        UserPatient userPatient = userPatientRetriever.getUserPatient(userId);

        Schedule schedule = Schedule.create(scheduleTime, userPatient);

        scheduleSaver.save(schedule);
    }

    @Transactional(readOnly = true)
    public GetTodayScheduleResponseDto getTodaySchedule(UUID userId) {
        Optional<Schedule> optionalSchedule = scheduleRetriever.getRecentSchedule(userId);

        if(optionalSchedule.isEmpty()){
            return GetTodayScheduleResponseDto.empty();
        }
        Schedule schedule = optionalSchedule.get();

        if(schedule.getScheduleTime().toLocalDate().equals(LocalDate.now())){
            return GetTodayScheduleResponseDto.of(ScheduleResponseDto.from(schedule), true);
        }else{
            return GetTodayScheduleResponseDto.of(ScheduleResponseDto.from(schedule), false);
        }
    }
}
