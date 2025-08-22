package hanium.dongguk.calendar.controller;

import hanium.dongguk.calendar.dto.request.SaveCalendarRequestDto;
import hanium.dongguk.calendar.dto.request.UpdateCalendarRequestDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.calendar.service.CalendarService;
import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController implements CalendarApiSwagger {

    private final CalendarService calendarService;

    @Override
    @GetMapping
    public ResponseEntity<CalendarResponseDto> getCalendar(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @UserId UUID userId) {
        return ResponseEntity.ok(calendarService.getCalendarByDate(userId, date));
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> saveCalendar(
            @Valid @RequestBody SaveCalendarRequestDto requestDto,
            @UserId UUID userId) {
        calendarService.saveCalendar(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @PatchMapping("/{calendarId}")
    public ResponseEntity<Void> updateCalendar(
            @PathVariable UUID calendarId,
            @Valid @RequestBody UpdateCalendarRequestDto requestDto,
            @UserId UUID userId) {

        calendarService.updateCalendar(userId, calendarId, requestDto);
        return ResponseEntity.ok().build();
    }
}