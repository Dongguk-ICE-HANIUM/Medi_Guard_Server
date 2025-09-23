package hanium.dongguk.schedule.service;

import hanium.dongguk.global.dto.PageResponseDto;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.global.util.RedisUtil;
import hanium.dongguk.schedule.domain.EScheduleStatus;
import hanium.dongguk.schedule.domain.Schedule;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.dto.request.VerifyCodeRequestDto;
import hanium.dongguk.schedule.dto.response.*;
import hanium.dongguk.schedule.exception.ScheduleErrorCode;
import hanium.dongguk.schedule.validator.ScheduleValidator;
import hanium.dongguk.user.doctor.domain.UserDoctor;
import hanium.dongguk.user.doctor.service.UserDoctorRetriever;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
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
    private final UserDoctorRetriever userDoctorRetriever;
    private final RedisUtil redisUtil;

    @Transactional
    public void saveSchedule(SaveScheduleRequestDto request, UUID userId) {

        LocalDateTime scheduleTime = request.scheduleTime();

        scheduleValidator.validateFutureScheduleTime(scheduleTime);

        if(scheduleRetriever.existsByScheduleTime(userId, scheduleTime)) {
            throw CommonException.type(ScheduleErrorCode.DUPLICATE_SCHEDULE_TIME);
        }

        UserPatient userPatient = userPatientRetriever.getUserPatient(userId);

        UserDoctor userDoctor = userDoctorRetriever.getUserDoctor(request.doctorId());

        Schedule schedule = Schedule.create(scheduleTime, userPatient, userDoctor);

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

    @Transactional(readOnly = true)
    public PageResponseDto<ScheduleResponseDto> getScheduleList(UUID userId, Integer page) {

        Pageable pageable = PageRequest.of(page, 4);

         Page<ScheduleResponseDto> schedulePage =  scheduleRetriever
                 .getCompletedScheduleList(userId, pageable)
                 .map(ScheduleResponseDto::from);

        return PageResponseDto.from(schedulePage);
    }

    @Transactional(readOnly = true)
    public GetScheduleDetailResponseDto getScheduleDetail(UUID userId, UUID scheduleId) {

        Schedule schedule = scheduleRetriever.getSchedule(userId, scheduleId);

        scheduleValidator.validateCompletedScheduleStatus(schedule.getStatus());

        return GetScheduleDetailResponseDto.from(schedule);
    }

    @Transactional
    public StartScheduleResponseDto startSchedule(UUID userId, UUID scheduleId) {

        Schedule schedule = scheduleRetriever.getSchedule(userId, scheduleId);

        scheduleValidator.validateWaitingScheduleStatus(schedule.getStatus());

        scheduleValidator.validateTodayScheduleDate(schedule.getScheduleTime());

        String code = redisUtil.generateAndStoreCode(ScheduleAuthDto.from(schedule), Duration.ofMinutes(2));

        schedule.startSchedule();

        return StartScheduleResponseDto.from(code);
    }

    @Transactional
    public VerifyCodeResponseDto verifyCode(UUID userId, VerifyCodeRequestDto request) {

        ScheduleAuthDto scheduleAuthDto = redisUtil.getAndValidateCode(request.code(), ScheduleAuthDto.class);

        Schedule schedule = scheduleRetriever.getSchedule(scheduleAuthDto.patientId(), scheduleAuthDto.scheduleId());

        scheduleValidator.validateDoctorOwnsSchedule(userId, schedule.getDoctor().getId());

        scheduleValidator.validateStartedScheduleStatus(schedule.getStatus());

        schedule.progressSchedule();

        return VerifyCodeResponseDto.from(schedule);
    }

@Transactional(readOnly = true)
    public CheckProgressScheduleResponseDto checkProgressSchedule(UUID userId, UUID scheduleId) {

        Schedule schedule = scheduleRetriever.getSchedule(userId, scheduleId);

        if(schedule.getStatus().equals(EScheduleStatus.IN_PROGRESS)){
            return CheckProgressScheduleResponseDto.progress();
        }
        if(schedule.getStatus().equals(EScheduleStatus.STARTED)){
            return CheckProgressScheduleResponseDto.started();
        }
        throw CommonException.type(ScheduleErrorCode.NOT_STARTED_SCHEDULE);
    }

    private record ScheduleAuthDto(
            UUID patientId,
            UUID scheduleId
    ){
        public static ScheduleAuthDto from(Schedule schedule){
            return new ScheduleAuthDto(schedule.getPatient().getId(), schedule.getId());
        }
    }
}
