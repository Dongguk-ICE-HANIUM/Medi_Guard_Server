package hanium.dongguk.calendar.controller;

import hanium.dongguk.calendar.dto.request.SaveCalendarDto;
import hanium.dongguk.calendar.dto.request.UpdateCalendarDto;
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
    public ResponseEntity<ResponseDto<CalendarResponseDto>> getCalendar(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @UserId UUID userId) {

        CalendarResponseDto response = calendarService.getCalendarByDate(userId, date);
        return ResponseEntity.ok(new ResponseDto<>(null, "OK", response));
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponseDto<List<Object>>> saveCalendar(
            @Valid @RequestBody SaveCalendarDto requestDto,
            @UserId UUID userId) {

        calendarService.saveCalendar(userId, requestDto);
        return ResponseEntity.ok(new ResponseDto<>(null, "OK", List.of()));
    }

    @Override
    @PatchMapping("/{calendarId}")
    public ResponseEntity<ResponseDto<List<Object>>> updateCalendar(
            @PathVariable UUID calendarId,
            @Valid @RequestBody UpdateCalendarDto requestDto,
            @UserId UUID userId) {

        calendarService.updateCalendar(userId, calendarId, requestDto);
        return ResponseEntity.ok(new ResponseDto<>(null, "OK", List.of()));
    }
}