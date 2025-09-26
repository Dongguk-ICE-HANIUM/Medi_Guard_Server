package hanium.dongguk.schedule.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.global.dto.PageResponseDto;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.dto.request.UpdateScheduleRequestDto;
import hanium.dongguk.schedule.dto.request.VerifyCodeRequestDto;
import hanium.dongguk.schedule.dto.response.*;
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
    @GetMapping
    public ResponseEntity<PageResponseDto<ScheduleResponseDto>> getScheduleList(@UserId UUID userId,
                                                           @RequestParam(defaultValue = "0") Integer page) {
        return ResponseEntity.ok(scheduleService.getScheduleList(userId, page));
    }

    @Override
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleDetailResponseDto> getScheduleDetail(@UserId UUID userId,
                                                                          @PathVariable UUID scheduleId) {
        return ResponseEntity.ok(scheduleService.getScheduleDetail(userId, scheduleId));
    }

    @Override
    @PostMapping("/{scheduleId}")
    public ResponseEntity<StartScheduleResponseDto> startSchedule(@UserId UUID userId,
                                                                  @PathVariable UUID scheduleId) {
        return ResponseEntity.ok(scheduleService.startSchedule(userId, scheduleId));
    }

    @Override
    @PostMapping("/verify-doctor")
    public ResponseEntity<VerifyCodeResponseDto> verifyCode(@UserId UUID userId,
                                                            @RequestBody @Valid VerifyCodeRequestDto request) {
        return ResponseEntity.ok(scheduleService.verifyCode(userId, request));
    }

    @Override
    @GetMapping("/{scheduleId}/status")
    public ResponseEntity<CheckProgressScheduleResponseDto> checkProgressSchedule(@UserId UUID userId,
                                                                                  @PathVariable UUID scheduleId) {
        return ResponseEntity.ok(scheduleService.checkProgressSchedule(userId, scheduleId));
    }

    @Override
    @PatchMapping("/{scheduleId}/record")
    public ResponseEntity<Void> updateSchedule(@UserId UUID userId,
                                               @PathVariable UUID scheduleId,
                                               @Valid @RequestBody UpdateScheduleRequestDto request
                                               ){
        scheduleService.updateSchedule(userId, scheduleId, request);
        return ResponseEntity.ok().build();
    }
}
