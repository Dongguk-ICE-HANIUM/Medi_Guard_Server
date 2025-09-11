package hanium.dongguk.schedule.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.global.dto.PageResponseDto;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.dto.response.GetScheduleDetailResponseDto;
import hanium.dongguk.schedule.dto.response.GetTodayScheduleResponseDto;
import hanium.dongguk.schedule.dto.response.ScheduleResponseDto;
import hanium.dongguk.schedule.dto.response.StartScheduleResponseDto;
import hanium.dongguk.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController implements ScheduleApiSwagger {

    private final ScheduleService scheduleService;

    @Override
    @PostMapping("")
    public ResponseEntity<Void> saveSchedule(
            @RequestBody @Valid SaveScheduleRequestDto request,
            @UserId UUID userId) {
        scheduleService.saveSchedule(request, userId);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/today")
    public ResponseEntity<GetTodayScheduleResponseDto> getTodaySchedule(@UserId UUID userId) {
        return ResponseEntity.ok(scheduleService.getTodaySchedule(userId));
    }

    @Override
    @GetMapping("")
    public ResponseEntity<PageResponseDto<ScheduleResponseDto>> getScheduleList(@UserId UUID userId,
                                                           @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(scheduleService.getScheduleList(userId, page));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleDetailResponseDto> getScheduleDetail(@UserId UUID userId,
                                                                          @PathVariable UUID scheduleId) {
        return ResponseEntity.ok(scheduleService.getScheduleDetail(userId, scheduleId));
    }

    @PostMapping("/{scheduleId}")
    public ResponseEntity<StartScheduleResponseDto> stratSchedule(@UserId UUID userId,
                                                                  @PathVariable UUID scheduleId){
        return ResponseEntity.ok(scheduleService.startSchedule(userId, scheduleId));
    }


}
