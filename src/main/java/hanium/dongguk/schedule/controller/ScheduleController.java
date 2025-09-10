package hanium.dongguk.schedule.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.dto.response.GetTodayScheduleResponseDto;
import hanium.dongguk.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController implements ScheduleApiSwagger {

    private final ScheduleService scheduleService;

    @PostMapping("")
    public void saveSchedule(@RequestBody SaveScheduleRequestDto request, @UserId UUID userId) {
        scheduleService.saveSchedule(request, userId);
    }

    @GetMapping("/today")
    public GetTodayScheduleResponseDto  getTodaySchedule(@UserId UUID userId) {
        return scheduleService.getTodaySchedule(userId);
    }
}
