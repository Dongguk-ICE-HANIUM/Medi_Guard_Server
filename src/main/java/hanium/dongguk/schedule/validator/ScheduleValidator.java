package hanium.dongguk.schedule.validator;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.schedule.domain.EScheduleStatus;
import hanium.dongguk.schedule.exception.ScheduleErrorCode;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ScheduleValidator {

    public void validateFutureScheduleTime(LocalDateTime scheduleTime){
        if(scheduleTime.isBefore(LocalDateTime.now())){
            throw CommonException.type(ScheduleErrorCode.INVALID_SCHEDULE_TIME);
        }
    }

    public void validateWaitingScheduleStatus(EScheduleStatus status){
        if(!status.equals(EScheduleStatus.WAITING)){
            throw CommonException.type(ScheduleErrorCode.NOT_WAITING_SCHEDULE);
        }
    }

    public void validateCompletedScheduleStatus(EScheduleStatus status){
        if(!status.equals(EScheduleStatus.COMPLETED)){
            throw CommonException.type(ScheduleErrorCode.NOT_COMPLETED_SCHEDULE);
        }
    }

    public void validateTodayScheduleDate(LocalDateTime scheduleTime){
        if(!scheduleTime.toLocalDate().equals(LocalDate.now())){
            throw CommonException.type(ScheduleErrorCode.NOT_TODAY_SCHEDULE_TIME);
        }
    }

    public void validateDoctorOwnsSchedule(UUID doctorId, UUID scheduleDoctorId){
        if(!scheduleDoctorId.equals(doctorId)){
            throw CommonException.type(ScheduleErrorCode.NOT_MATCH_DOCTOR_AND_SCHEDULE);
        }
    }

    public void validateStartedScheduleStatus(EScheduleStatus status){
        if(!status.equals(EScheduleStatus.STARTED)){
            throw CommonException.type(ScheduleErrorCode.NOT_STARTED_SCHEDULE);
        }
    }
}
