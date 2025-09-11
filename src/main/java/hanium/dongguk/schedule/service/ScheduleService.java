package hanium.dongguk.schedule.service;

import hanium.dongguk.global.dto.PageResponseDto;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.schedule.domain.Schedule;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.dto.response.GetScheduleDetailResponseDto;
import hanium.dongguk.schedule.dto.response.GetTodayScheduleResponseDto;
import hanium.dongguk.schedule.dto.response.ScheduleResponseDto;
import hanium.dongguk.schedule.dto.response.StartScheduleResponseDto;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleSaver scheduleSaver;
    private final ScheduleValidator scheduleValidator;
    private final UserPatientRetriever userPatientRetriever;
    private final ScheduleRetriever scheduleRetriever;
    private final UserDoctorRetriever userDoctorRetriever;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void saveSchedule(SaveScheduleRequestDto request, UUID userId) {

        LocalDateTime scheduleTime = request.scheduleTime();

        scheduleValidator.validateFutureScheduleTime(scheduleTime);

        if(scheduleRetriever.existsByScheduleTime(userId, scheduleTime)) {
            throw CommonException.type(ScheduleErrorCode.DUPLICATE_SCHEDULE_TIME);
        }

        UserPatient userPatient = userPatientRetriever.getUserPatient(userId);

        UUID doctorUUID = UUID.fromString("ab36bc1a-8ece-11f0-80f6-00155da312b9");

        UserDoctor userDoctor = userDoctorRetriever.getUserDoctor(doctorUUID);

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

    @Transactional(readOnly = true)
    public StartScheduleResponseDto startSchedule(UUID userId, UUID scheduleId) {

        Schedule schedule = scheduleRetriever.getSchedule(userId, scheduleId);

        scheduleValidator.validateWaitingScheduleStatus(schedule.getStatus());

        scheduleValidator.validateTodayScheduleDate(schedule.getScheduleTime());

        String authCode = generateAuthCode(scheduleId);

        return StartScheduleResponseDto.from(authCode);

    }

    private String generateAuthCode(UUID scheduleId){
        String code = String.format("%06d", new Random().nextInt(1000000));
        String redisKey = "schedule:auth:" + scheduleId.toString();
        redisTemplate.opsForValue().set(redisKey, code, Duration.ofMinutes(10));

        return code;
    }
}
