package hanium.dongguk.schedule.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
