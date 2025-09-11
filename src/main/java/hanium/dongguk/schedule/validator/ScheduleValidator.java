package hanium.dongguk.schedule.validator;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.schedule.domain.EScheduleStatus;
import hanium.dongguk.schedule.exception.ScheduleErrorCode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduleValidator {

    public void validateScheduleTime(LocalDateTime scheduleTime){
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

}
