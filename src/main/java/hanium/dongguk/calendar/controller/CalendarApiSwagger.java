package hanium.dongguk.calendar.controller;

import hanium.dongguk.calendar.dto.request.CalendarSaveRequestDto;
import hanium.dongguk.calendar.dto.request.CalendarUpdateRequestDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

@Tag(name = "Calendar", description = "캘린더 관련 API")
public interface CalendarApiSwagger {

    @Operation(summary = "오늘의 기분 조회", description = "특정 날짜의 기분을 조회합니다.")
    ResponseEntity<ResponseDto<CalendarResponseDto>> getCalendar(
            @Parameter(description = "조회할 날짜", example = "2025-08-04")
            LocalDate date,
            UUID userId
    );

    @Operation(summary = "오늘의 기분 생성", description = "새로운 기분을 생성합니다.")
    ResponseEntity<ResponseDto<Void>> saveCalendar(
            CalendarSaveRequestDto requestDto,
            UUID userId
    );

    @Operation(summary = "오늘의 기분 수정", description = "기존 기분을 수정합니다.")
    ResponseEntity<ResponseDto<Void>> updateCalendar(
            @Parameter(description = "캘린더 ID")
            UUID calendarId,
            CalendarUpdateRequestDto requestDto,
            UUID userId
    );
}