package hanium.dongguk.schedule.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.dto.response.GetTodayScheduleResponseDto;
import hanium.dongguk.schedule.dto.response.ScheduleResponseDto;
import hanium.dongguk.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController implements ScheduleApiSwagger {

    private final ScheduleService scheduleService;

    @PostMapping("")
    public ResponseEntity<Void> saveSchedule(@RequestBody SaveScheduleRequestDto request, @UserId UUID userId) {
        scheduleService.saveSchedule(request, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/today")
    public ResponseEntity<GetTodayScheduleResponseDto> getTodaySchedule(@UserId UUID userId) {
        return ResponseEntity.ok(scheduleService.getTodaySchedule(userId));
    }

    @GetMapping("")
    public ResponseEntity<Page<ScheduleResponseDto>> getScheduleList(@UserId UUID userId,
                                                                     @PageableDefault(size = 4) Pageable pageable) {
        return ResponseEntity.ok(scheduleService.getScheduleList(userId, pageable));
    }
}
