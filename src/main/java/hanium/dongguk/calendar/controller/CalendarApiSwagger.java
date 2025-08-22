package hanium.dongguk.calendar.controller;

import hanium.dongguk.calendar.dto.request.SaveCalendarDto;
import hanium.dongguk.calendar.dto.request.UpdateCalendarDto;
import hanium.dongguk.calendar.dto.response.CalendarResponseDto;
import hanium.dongguk.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(name = "Calendar", description = "오늘의 기분 관리 API")
public interface CalendarApiSwagger {

    @Operation(
            summary = "오늘의 기분 조회",
            description = """
                    로그인된 환자의 특정 날짜 기분 기록을 조회합니다.
                    
                    **주요 기능:**
                    - 특정 날짜의 감정 상태 조회
                    - 기분 설명 텍스트 조회
                    - 질문 유형 정보 조회
                    - 환자별 개인화된 기분 이력 관리
                    
                    **조회 규칙:**
                    - 날짜: 필수 입력값이며 ISO 날짜 형식(YYYY-MM-DD)
                    - 본인의 기분 기록만 조회 가능
                    - 데이터가 없는 경우 404 응답
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "기분 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CalendarResponseDto.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "기분 조회 완료",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "OK",
                                                "result": {
                                                    "emotion": "NEUTRAL",
                                                    "description": "두통이 있고 어지럽다."
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "캘린더를 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(
                            value = """
                                    {
                                        "errorCode": "CALENDAR_NOT_FOUND",
                                        "message": "해당 날짜의 캘린더를 찾을 수 없습니다.",
                                        "result": null
                                    }
                                    """
                    ))
            )
    })
    ResponseEntity<ResponseDto<CalendarResponseDto>> getCalendar(
            @Parameter(
                    description = "조회할 기분 기록 날짜 (ISO 날짜 형식: YYYY-MM-DD)",
                    required = true,
                    example = "2025-08-04"
            )
            LocalDate date,
            UUID userId
    );

    @Operation(
            summary = "오늘의 기분 생성",
            description = """
                    로그인된 환자의 새로운 기분 기록을 생성합니다.
                    
                    **주요 기능:**
                    - 감정 상태 기록 저장
                    - 기분 설명 텍스트 저장
                    - 질문 유형 정보 저장
                    - 중복 기록 방지 검증
                    
                    **검증 규칙:**
                    - 날짜: 필수 입력값이며 ISO 날짜 형식(YYYY-MM-DD)
                    - 감정: HAPPY, SAD, ANGRY, ANXIOUS, NEUTRAL 중 하나
                    - 설명: 필수 입력값 (최대 1000자)
                    - 질문 유형: 유효한 질문 타입이어야 함
                    - 중복 방지: 동일 날짜 기록이 이미 있으면 생성 불가
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "기분 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "기분 생성 완료",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "OK",
                                                "result": []
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 존재하는 캘린더",
                    content = @Content(examples = @ExampleObject(
                            value = """
                                    {
                                        "errorCode": "CALENDAR_ALREADY_EXISTS",
                                        "message": "해당 날짜의 캘린더가 이미 존재합니다.",
                                        "result": null
                                    }
                                    """
                    ))
            )
    })
    ResponseEntity<ResponseDto<List<Object>>> saveCalendar(
            @Parameter(
                    description = "기분 저장 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveCalendarDto.class),
                            examples = @ExampleObject(
                                    name = "기분 저장 요청 예시",
                                    summary = "정상적인 기분 저장 요청",
                                    value = """
                                            {
                                                "date": "2025-08-04",
                                                "emotion": "NEUTRAL",
                                                "description": "두통이 있고 어지럽다.",
                                                "questionType": "PHYSICAL_SYMPTOMS"
                                            }
                                            """
                            )
                    )
            )
            SaveCalendarDto requestDto,
            UUID userId
    );

    @Operation(
            summary = "오늘의 기분 부분 수정",
            description = """
                    로그인된 환자의 기존 기분 기록을 부분적으로 수정합니다.
                    
                    **주요 기능:**
                    - 감정 상태 부분 변경
                    - 기분 설명 텍스트 부분 수정
                    - 기록 소유자 권한 검증
                    - 기록 존재 여부 확인
                    
                    **검증 규칙:**
                    - 캘린더 ID: 유효한 UUID 형식이어야 함
                    - 감정: VERY_HAPPY, HAPPY, NEUTRAL, SAD, ANGRY 중 하나
                    - 설명: 필수 입력값 (최대 1000자)
                    - 권한: 본인의 기록만 수정 가능
                    - 존재성: 수정할 기록이 존재해야 함
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "기분 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "기분 수정 완료",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "OK",
                                                "result": []
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "캘린더를 찾을 수 없음",
                    content = @Content(examples = @ExampleObject(
                            value = """
                                    {
                                        "errorCode": "CALENDAR_NOT_FOUND",
                                        "message": "해당 캘린더를 찾을 수 없습니다.",
                                        "result": null
                                    }
                                    """
                    ))
            )
    })
    ResponseEntity<ResponseDto<List<Object>>> updateCalendar(
            @Parameter(
                    description = "수정할 기분 기록의 캘린더 ID",
                    required = true,
                    example = "f47ac10b-58cc-4372-a567-0e02b2c3d479"
            )
            UUID calendarId,
            @Parameter(
                    description = "기분 수정 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateCalendarDto.class),
                            examples = @ExampleObject(
                                    name = "기분 수정 요청 예시",
                                    summary = "정상적인 기분 수정 요청",
                                    value = """
                                            {
                                                "emotion": "HAPPY",
                                                "description": "기분이 많이 좋아졌습니다."
                                            }
                                            """
                            )
                    )
            )
            UpdateCalendarDto requestDto,
            UUID userId
    );
}