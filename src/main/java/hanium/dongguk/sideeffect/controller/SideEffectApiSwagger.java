package hanium.dongguk.sideeffect.controller;

import hanium.dongguk.sideeffect.dto.request.SaveSideEffectRequestDto;
import hanium.dongguk.sideeffect.dto.request.UpdateSideEffectRequestDto;
import hanium.dongguk.sideeffect.dto.response.SideEffectResponseDto;
import hanium.dongguk.sideeffect.dto.response.SideEffectListResponseDto;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "SideEffect", description = "약물 부작용 관리 API")
public interface SideEffectApiSwagger {

    @Operation(
            summary = "부작용 기록 생성",
            description = """
                    로그인된 환자의 새로운 부작용 기록을 생성합니다.
                    
                    **주요 기능:**
                    - 특정 약물(CalendarDrug)에 대한 부작용 기록 생성
                    - 부작용 증상 상세 설명 저장
                    - 복용 기록과 연계된 부작용 추적
                    - 환자별 개인화된 부작용 이력 관리
                    
                    **검증 규칙:**
                    - 약물 ID: 유효한 CalendarDrug ID여야 함
                    - 설명: 필수 입력값
                    - 권한: 본인의 약물 복용 기록에만 부작용 추가 가능
                    - 존재성: 해당 CalendarDrug가 존재해야 함
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "부작용 기록 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "부작용 기록 생성 완료",
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
                                            name = "필수값 누락",
                                            summary = "부작용 설명 누락",
                                            value = """
                                                    {
                                                       "errorCode": "SIDE_EFFECT_002",
                                                       "message": "부작용 설명을 입력해주세요.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "접근 권한 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "권한 없음",
                                    summary = "본인의 약물에만 부작용 추가 가능",
                                    value = """
                                            {
                                               "errorCode": "SIDE_EFFECT_004",
                                               "message": "본인의 부작용 기록만 접근할 수 있습니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "약물 기록을 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "약물 없음",
                                    summary = "해당 캘린더 약물 기록이 없음",
                                    value = """
                                            {
                                               "errorCode": "SIDE_EFFECT_003",
                                               "message": "해당 캘린더 약물 기록이 존재하지 않습니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Void> saveSideEffect(
            @Parameter(
                    description = "부작용 저장 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveSideEffectRequestDto.class),
                            examples = @ExampleObject(
                                    name = "부작용 저장 요청 예시",
                                    summary = "정상적인 부작용 저장 요청",
                                    value = """
                                            {
                                                "id": "550e8400-e29b-41d4-a716-446655440000",
                                                "description": "두통이 심하고 어지러움증이 있습니다."
                                            }
                                            """
                            )
                    )
            )
            @RequestBody SaveSideEffectRequestDto requestDto,
            UUID userId
    );

    @Operation(
            summary = "부작용 기록 목록 조회",
            description = """
                    로그인된 환자의 모든 부작용 기록을 조회합니다.
                    
                    **주요 기능:**
                    - 환자의 전체 부작용 기록 목록 조회
                    - 약물명과 함께 부작용 정보 제공
                    - 시간순 정렬된 부작용 이력 관리
                    - 환자별 개인화된 부작용 통계
                    
                    **조회 규칙:**
                    - 본인의 부작용 기록만 조회 가능
                    - 최신 기록부터 시간순 정렬
                    - 연관된 약물 정보도 함께 제공
                    - 빈 목록인 경우에도 200 응답
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "부작용 기록 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SideEffectListResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "성공 응답 (데이터 있음)",
                                            summary = "부작용 기록이 있는 경우",
                                            value = """
                                                    {
                                                        "errorCode": null,
                                                        "message": "OK",
                                                        "result": {
                                                            "sideEffectList": [
                                                                {
                                                                    "id": "550e8400-e29b-41d4-a716-446655440000",
                                                                    "drug_name": "타이레놀",
                                                                    "description": "두통이 심하고 어지러움증이 있습니다."
                                                                },
                                                                {
                                                                    "id": "550e8400-e29b-41d4-a716-446655440001",
                                                                    "drug_name": "게보린",
                                                                    "description": "위장 장애와 메스꺼움이 발생했습니다."
                                                                },
                                                                {
                                                                    "id": "550e8400-e29b-41d4-a716-446655440002",
                                                                    "drug_name": "애드빌",
                                                                    "description": "졸음과 집중력 저하 증상이 나타났습니다."
                                                                }
                                                            ]
                                                        }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "성공 응답 (빈 목록)",
                                            summary = "부작용 기록이 없는 경우",
                                            value = """
                                                    {
                                                        "errorCode": null,
                                                        "message": "OK",
                                                        "result": {
                                                            "sideEffectList": []
                                                        }
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
    ResponseEntity<SideEffectListResponseDto> getSideEffect(UUID userId);

    @Operation(
            summary = "부작용 기록 수정",
            description = """
                    로그인된 환자의 기존 부작용 기록을 수정합니다.
                    
                    **주요 기능:**
                    - 기존 부작용 기록의 설명 내용 수정
                    - 부작용 증상 변화 추적
                    - 수정 이력 관리 및 추적
                    - 권한 기반 접근 제어
                    
                    **검증 규칙:**
                    - 부작용 ID: 유효한 UUID 형식이어야 함
                    - 설명: 필수 입력값 (최대 1000자)
                    - 권한: 본인의 부작용 기록만 수정 가능
                    - 존재성: 수정할 부작용 기록이 존재해야 함
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "부작용 기록 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "부작용 기록 수정 완료",
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
                            examples = @ExampleObject(
                                    name = "필수값 누락",
                                    summary = "부작용 설명 누락",
                                    value = """
                                            {
                                               "errorCode": "SIDE_EFFECT_002",
                                               "message": "부작용 설명을 입력해주세요.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "부작용 기록을 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "부작용 기록 없음",
                                    summary = "해당 부작용 기록이 존재하지 않음",
                                    value = """
                                            {
                                               "errorCode": "SIDE_EFFECT_001",
                                               "message": "부작용 기록을 찾을 수 없습니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Void> updateSideEffect(
            @Parameter(
                    description = "수정할 부작용 기록의 ID",
                    required = true,
                    example = "f47ac10b-58cc-4372-a567-0e02b2c3d479"
            )
            UUID sideEffectId,
            @Parameter(
                    description = "부작용 수정 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateSideEffectRequestDto.class),
                            examples = @ExampleObject(
                                    name = "부작용 수정 요청 예시",
                                    summary = "정상적인 부작용 수정 요청",
                                    value = """
                                            {
                                                "description": "두통 증상이 많이 완화되었지만, 여전히 가벼운 어지러움이 있습니다. 복용량을 조절해야 할 것 같습니다."
                                            }
                                            """
                            )
                    )
            )
            @RequestBody UpdateSideEffectRequestDto requestDto,
            UUID userId
    );

    @Operation(
            summary = "부작용 기록 삭제",
            description = """
                    로그인된 환자의 기존 부작용 기록을 삭제합니다.
                    
                    **주요 기능:**
                    - 불필요한 부작용 기록 삭제
                    - 잘못 입력된 데이터 제거
                    - 개인정보 관리 및 정리
                    - 권한 기반 접근 제어
                    
                    **검증 규칙:**
                    - 부작용 ID: 유효한 UUID 형식이어야 함
                    - 권한: 본인의 부작용 기록만 삭제 가능
                    - 존재성: 삭제할 부작용 기록이 존재해야 함
                    - 영구 삭제: 삭제 후 복구 불가능
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "부작용 기록 삭제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "부작용 기록 삭제 완료",
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
                    description = "부작용 기록을 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "부작용 기록 없음",
                                    summary = "해당 부작용 기록이 존재하지 않음",
                                    value = """
                                            {
                                               "errorCode": "SIDE_EFFECT_001",
                                               "message": "부작용 기록을 찾을 수 없습니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<Void> deleteSideEffect(
            @Parameter(
                    description = "삭제할 부작용 기록의 ID",
                    required = true,
                    example = "f47ac10b-58cc-4372-a567-0e02b2c3d479"
            )
            UUID sideEffectId,
            UUID userId
    );
}