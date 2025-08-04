package hanium.dongguk.calendar.controller;

import hanium.dongguk.calendar.dto.request.CalendarSaveRequestDto;
import hanium.dongguk.calendar.dto.request.CalendarUpdateRequestDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.calendar.service.CalendarService;
import hanium.dongguk.user.core.dto.UserSecurityForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
@Tag(name = "오늘의 기분", description = "캘린더(오늘의 기분) 저장, 수정, 조회 기능 제공")
public class CalendarController {

    private final CalendarService calendarService;

    /**
     * 오늘의 기분 저장
     */
    @Operation(
            summary = "오늘의 기분 저장",
            description = "로그인된 환자의 오늘의 기분을 저장합니다."
    )
    @PostMapping
    public ResponseEntity<CalendarResponseDto> saveCalendar(
            @AuthenticationPrincipal UserSecurityForm loginUser,
            @RequestBody CalendarSaveRequestDto requestDto) {

        return ResponseEntity.ok(
                calendarService.saveCalendar(loginUser.getId(), requestDto)
        );
    }

    /**
     * 오늘의 기분 수정
     */
    @Operation(
            summary = "오늘의 기분 수정",
            description = "로그인된 환자의 오늘의 기분을 수정합니다."
    )
    @PutMapping
    public ResponseEntity<CalendarResponseDto> updateCalendar(
            @AuthenticationPrincipal UserSecurityForm loginUser,
            @RequestBody CalendarUpdateRequestDto requestDto) {

        return ResponseEntity.ok(
                calendarService.updateCalendar(loginUser.getId(), requestDto)
        );
    }

    /**
     * 오늘의 기분 조회
     */
    @Operation(
            summary = "오늘의 기분 조회",
            description = "로그인된 환자의 특정 날짜 기분을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<CalendarResponseDto> getTodayMood(
            @AuthenticationPrincipal UserSecurityForm loginUser,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(
                calendarService.getTodayMood(loginUser.getId(), date)
        );
    }
}