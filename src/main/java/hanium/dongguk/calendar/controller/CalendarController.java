package hanium.dongguk.calendar.controller;

import hanium.dongguk.calendar.dto.request.CalendarSaveRequestDto;
import hanium.dongguk.calendar.dto.request.CalendarUpdateRequestDto;
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
        return ResponseEntity.ok(ResponseDto.success(response));
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponseDto<List<Object>>> saveCalendar(
            @Valid @RequestBody CalendarSaveRequestDto requestDto,
            @UserId UUID userId) {

        calendarService.saveCalendar(userId, requestDto);
        return ResponseEntity.ok(ResponseDto.success(List.of()));
    }

    @Override
    @PutMapping("/{calendarId}")
    public ResponseEntity<ResponseDto<List<Object>>> updateCalendar(
            @PathVariable UUID calendarId,
            @Valid @RequestBody CalendarUpdateRequestDto requestDto,
            @UserId UUID userId) {

        calendarService.updateCalendar(userId, calendarId, requestDto);
        return ResponseEntity.ok(ResponseDto.success(List.of()));
    }
}