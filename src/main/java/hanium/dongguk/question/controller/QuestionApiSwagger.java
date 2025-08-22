package hanium.dongguk.question.controller;

import hanium.dongguk.question.dto.request.SaveQuestionListRequestDto;
import hanium.dongguk.question.dto.request.UpdateQuestionListRequestDto;
import hanium.dongguk.question.dto.response.QuestionResponseDto;
import hanium.dongguk.global.annotation.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;

@Tag(name = "Question", description = "오늘의 질문 관리 API")
public interface QuestionApiSwagger {

    @Operation(
            summary = "오늘의 질문 저장",
            description = """
                    로그인된 환자의 오늘의 질문을 저장합니다.
                    
                    **주요 기능:**
                    - 특정 날짜에 대한 질문 응답 저장
                    - 환자별 질문 데이터 관리
                    - 중복 질문 처리 및 검증
                    
                    **검증 규칙:**
                    - 날짜: 필수 입력값이며 ISO 날짜 형식(YYYY-MM-DD)
                    - 질문 응답: 각 질문 유형별 응답 필수
                    - 인증: 로그인된 사용자만 접근 가능
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "질문 저장 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = QuestionResponseDto.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "질문 저장 완료",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "OK",
                                                "result": {}
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 - 입력값 검증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "날짜 형식 오류",
                                            summary = "잘못된 날짜 형식",
                                            value = """
                                                    {
                                                       "errorCode": "QUESTION_001",
                                                       "message": "유효하지 않은 날짜 형식입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "필수값 누락",
                                            summary = "필수 응답 값 누락",
                                            value = """
                                                    {
                                                       "errorCode": "QUESTION_002",
                                                       "message": "모든 질문에 대한 응답이 필요합니다.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "인증 실패",
                                    summary = "로그인이 필요합니다",
                                    value = """
                                            {
                                               "errorCode": "AUTH_001",
                                               "message": "인증이 필요합니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Void> saveQuestions(
            @Parameter(
                    description = "질문 날짜 (ISO 날짜 형식: YYYY-MM-DD)",
                    required = true,
                    example = "2025-08-17"
            )
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(
                    description = "질문 저장 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveQuestionListRequestDto.class),
                            examples = @ExampleObject(
                                    name = "질문 저장 요청 예시",
                                    summary = "정상적인 질문 저장 요청",
                                    value = """
                                            {
                                                "questionList": [
                                                    {
                                                        "type": "PHYSICAL_SYMPTOMS",
                                                        "answer": "두통이 있고 어지럽다."
                                                    },
                                                    {
                                                        "type": "PATIENT_CONCERNS",
                                                        "answer": "약물 부작용이 걱정됩니다."
                                                    },
                                                    {
                                                        "type": "MEDICATION_COMPLIANCE",
                                                        "answer": "매일 규칙적으로 복용하고 있습니다."
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody SaveQuestionListRequestDto requestDto,
            @UserId UUID userId
    );

    @Operation(
            summary = "오늘의 질문 수정",
            description = """
                    로그인된 환자의 기존 질문 응답을 수정합니다.
                    
                    **주요 기능:**
                    - 특정 날짜의 기존 질문 응답 수정
                    - 부분 수정 지원 (일부 응답만 변경 가능)
                    - 수정 이력 관리
                    
                    **검증 규칙:**
                    - 날짜: 필수 입력값이며 ISO 날짜 형식(YYYY-MM-DD)
                    - 기존 질문 데이터 존재 여부 확인
                    - 수정 권한 검증 (본인 질문만 수정 가능)
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "질문 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = QuestionResponseDto.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "질문 수정 완료",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "OK",
                                                "result": {}
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "질문 데이터를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "캘린더 없음",
                                            summary = "해당 날짜의 캘린더가 없음",
                                            value = """
                                                    {
                                                       "errorCode": "CALENDAR_001",
                                                       "message": "해당 날짜의 캘린더를 찾을 수 없습니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "질문 타입 없음",
                                            summary = "해당 타입의 질문이 없음",
                                            value = """
                                                    {
                                                       "errorCode": "QUESTION_004",
                                                       "message": "해당하는 유형 질문이 없습니다.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<Void> updateQuestions(
            @Parameter(
                    description = "질문 날짜 (ISO 날짜 형식: YYYY-MM-DD)",
                    required = true,
                    example = "2025-08-17"
            )
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(
                    description = "질문 수정 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateQuestionListRequestDto.class),
                            examples = @ExampleObject(
                                    name = "질문 수정 요청 예시",
                                    summary = "정상적인 질문 수정 요청",
                                    value = """
                                            {
                                                "questionList": [
                                                    {
                                                        "type": "MEDICATION_COMPLIANCE",
                                                        "answer": "약물 복용을 잊어버린 날이 있었습니다."
                                                    },
                                                    {
                                                        "type": "MEDICATION_SIDE_EFFECTS",
                                                        "answer": "가벼운 메스꺼움이 있었습니다."
                                                    },
                                                    {
                                                        "type": "PHYSICAL_SYMPTOMS",
                                                        "answer": "증상이 이전보다 악화된 것 같습니다."
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody UpdateQuestionListRequestDto requestDto,
            @UserId UUID userId
    );

    @Operation(
            summary = "오늘의 질문 조회",
            description = """
                    로그인된 환자의 특정 날짜 질문 응답을 조회합니다.
                    
                    **주요 기능:**
                    - 특정 날짜의 질문 응답 데이터 조회
                    - 환자별 개인화된 질문 이력 관리
                    - 응답 통계 및 분석 데이터 제공
                    
                    **조회 규칙:**
                    - 날짜: 필수 입력값이며 ISO 날짜 형식(YYYY-MM-DD)
                    - 본인의 질문 데이터만 조회 가능
                    - 데이터가 없는 경우 404 응답
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "질문 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = QuestionResponseDto.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "질문 조회 완료",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "OK",
                                                "result": {
                                                    "questionList": [
                                                        {
                                                            "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                                                            "type": "PHYSICAL_SYMPTOMS",
                                                            "answer": "두통이 있고 어지럽다."
                                                        },
                                                        {
                                                            "id": "a47bc10b-58cc-4372-a567-0e02b2c3d480",
                                                            "type": "PATIENT_CONCERNS",
                                                            "answer": "약물 부작용이 걱정됩니다."
                                                        }
                                                    ]
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "질문 데이터를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "캘린더 없음",
                                            summary = "해당 날짜의 캘린더가 없음",
                                            value = """
                                                    {
                                                       "errorCode": "CALENDAR_001",
                                                       "message": "해당 날짜의 캘린더를 찾을 수 없습니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "질문 타입 없음",
                                            summary = "해당 타입의 질문이 없음",
                                            value = """
                                                    {
                                                       "errorCode": "QUESTION_004",
                                                       "message": "해당하는 유형 질문이 없습니다.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<QuestionResponseDto> getQuestionsByDate(
            @Parameter(
                    description = "조회할 질문 날짜 (ISO 날짜 형식: YYYY-MM-DD)",
                    required = true,
                    example = "2025-08-17"
            )
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @UserId UUID userId
    );
}