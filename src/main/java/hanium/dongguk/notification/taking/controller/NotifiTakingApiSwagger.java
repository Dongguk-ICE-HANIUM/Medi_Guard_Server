package hanium.dongguk.notification.taking.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.notification.taking.dto.request.NotifiTakingRequestDto;
import hanium.dongguk.notification.taking.dto.response.RetrieveNotifiTakingResponseDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "NotifiTaking", description = "환자 약물 알림 API")
public interface NotifiTakingApiSwagger {

    @Operation(
            summary = "약물 알림 생성",
            description = """
                    환자 약물에 대한 복용 알림을 생성합니다.
                    
                    **주요 기능:**
                    - 특정 환자 약물에 대한 복용 알림 시간 설정
                    - 여러 개의 알림 시간을 한 번에 등록
                    - 알림 활성화/비활성화 상태 설정
                    - 중복 시간 검증 및 방지
                    
                    **검증 규칙:**
                    - 알림 시간: 필수, LocalTime 형식 (HH:mm:ss)
                    - 활성화 여부: 필수, Boolean 값
                    - 중복 시간: 같은 환자 약물에서 동일한 알림 시간 불가
                    - 요청 내 중복: 요청 데이터 내에서도 중복 시간 자동 제거
                    
                    **사용 시나리오:**
                    - 새로운 약물 등록 후 복용 알림 설정
                    - 기존 약물에 추가 알림 시간 설정
                    - 복용 패턴 변경 시 새로운 알림 추가
                    
                    **권한:**
                    - 해당 사용자가 등록한 환자 약물만 접근 가능
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "알림 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "알림 생성 완료",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "SUCCESS",
                                                "result": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "필드 검증 오류",
                                            summary = "필수 필드 누락 또는 유효하지 않은 값",
                                            value = """
                                                    {
                                                        "success": false,
                                                        "message": "입력값이 올바르지 않습니다",
                                                        "errors": {
                                                            "notifiTakingDtoList": "약물 알림 리스트가 누락되었습니다.",
                                                            "notifiTakingDtoList[0].time": "약물 알림 시간이 누락되었습니다.",
                                                            "notifiTakingDtoList[0].isActive": "약물 알림 활성화 여부가 누락되었습니다."
                                                        }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "중복 시간 오류",
                                            summary = "이미 존재하는 알림 시간",
                                            value = """
                                                    {
                                                        "errorCode": "NOTIFI_TAKING_002",
                                                        "message": "복약 알림 시간이 이미 존재합니다.",
                                                        "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리소스를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "사용자 없음",
                                            summary = "존재하지 않는 사용자",
                                            value = """
                                                    {
                                                       "errorCode": "USER_001",
                                                       "message": "존재하지 않는 사용자입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "환자 약물 없음",
                                            summary = "존재하지 않는 환자 약물 또는 권한 없음",
                                            value = """
                                                    {
                                                       "errorCode": "PATIENT_DRUG_001",
                                                       "message": "존재하지 않는 환자 약물입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @PostMapping
    ResponseEntity<?> createNotifiTaking(
            @UserId UUID userId,
            @PathVariable("patientDrugId") UUID patientDrugId,
            @Valid @RequestBody NotifiTakingRequestDto requestDto);


    @Operation(
            summary = "약물 알림 수정",
            description = """
                    기존 약물 알림의 시간 및 활성화 상태를 수정합니다.
                    
                    **주요 기능:**
                    - 기존 알림의 시간 변경
                    - 알림 활성화/비활성화 상태 변경
                    - 여러 개의 알림을 한 번에 수정
                    - 중복 시간 검증 (다른 기존 알림과의 충돌 방지)
                    
                    **검증 규칙:**
                    - 알림 ID: 필수, 존재하고 해당 환자 약물에 속한 알림이어야 함
                    - 알림 시간: 필수, LocalTime 형식 (HH:mm:ss)
                    - 활성화 여부: 필수, Boolean 값
                    - 중복 시간: 수정되지 않는 다른 알림과 시간이 겹치면 안됨
                    - 요청 내 중복: 요청 데이터 내에서도 중복 시간 자동 제거
                    
                    **동작 방식:**
                    - 각 알림 ID에 해당하는 기존 알림 조회
                    - 수정되지 않는 다른 알림들과 시간 충돌 검사
                    - 문제가 없으면 시간 및 활성화 상태 업데이트
                    
                    **권한:**
                    - 해당 사용자가 등록한 환자 약물의 알림만 수정 가능
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "알림 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "알림 수정 완료",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "SUCCESS",
                                                "result": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "필드 검증 오류",
                                            summary = "필수 필드 누락 또는 유효하지 않은 값",
                                            value = """
                                                    {
                                                        "success": false,
                                                        "message": "입력값이 올바르지 않습니다",
                                                        "errors": {
                                                            "notifiTakingDtoList": "약물 알림 리스트가 누락되었습니다.",
                                                            "notifiTakingDtoList[0].time": "약물 알림 시간이 누락되었습니다.",
                                                            "notifiTakingDtoList[0].isActive": "약물 알림 활성화 여부가 누락되었습니다."
                                                        }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "중복 시간 오류",
                                            summary = "다른 기존 알림과 시간이 겹치는 경우",
                                            value = """
                                                    {
                                                        "errorCode": "NOTIFI_TAKING_002",
                                                        "message": "복약 알림 시간이 이미 존재합니다.",
                                                        "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "알림 ID 값 누락",
                                            summary = "수정할 알림 ID 값을 누락한 경우",
                                            value = """
                                                    {
                                                        "errorCode": "NOTIFI_TAKING_003",
                                                        "message": "알림을 수정할 ID 값이 누락되어있습니다.",
                                                        "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리소스를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "사용자 없음",
                                            summary = "존재하지 않는 사용자",
                                            value = """
                                                    {
                                                       "errorCode": "USER_001",
                                                       "message": "존재하지 않는 사용자입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "환자 약물 없음",
                                            summary = "존재하지 않는 환자 약물 또는 권한 없음",
                                            value = """
                                                    {
                                                       "errorCode": "PATIENT_DRUG_001",
                                                       "message": "존재하지 않는 환자 약물입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "알림 없음",
                                            summary = "존재하지 않는 알림 또는 권한 없음",
                                            value = """
                                                    {
                                                        "errorCode": "NOTIFI_TAKING_001",
                                                        "message": "존재하지 않는 복약 알림입니다.",
                                                        "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @PatchMapping
    ResponseEntity<?> patchNotifiTaking(
            @UserId UUID userId,
            @PathVariable("patientDrugId") UUID patientDrugId,
            @Valid @RequestBody NotifiTakingRequestDto requestDto);


    @Operation(
            summary = "약물 알림 삭제",
            description = """
                    특정 약물 알림을 삭제합니다.
                    
                    **주요 기능:**
                    - 개별 약물 알림 완전 삭제
                    - 되돌릴 수 없는 영구 삭제
                    - 해당 사용자 권한 검증
                    
                    **주의사항:**
                    - 삭제된 알림은 복구할 수 없습니다
                    - 일시적 중단이 목적이라면 PATCH로 isActive를 false로 설정하세요
                    - 알림이 이미 삭제되었거나 존재하지 않아도 안전하게 처리됩니다
                    
                    **사용 시나리오:**
                    - 불필요한 알림 시간 제거
                    - 복용 패턴 변경으로 인한 알림 삭제
                    - 약물 중단 시 관련 알림 정리
                    
                    **권한:**
                    - 해당 사용자가 등록한 알림만 삭제 가능
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "알림 삭제 성공 (No Content)",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리소스를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "사용자 없음",
                                            summary = "존재하지 않는 사용자",
                                            value = """
                                                    {
                                                       "errorCode": "USER_001",
                                                       "message": "존재하지 않는 사용자입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "알림 없음",
                                            summary = "존재하지 않는 알림 또는 권한 없음",
                                            value = """
                                                    {
                                                        "errorCode": "NOTIFI_TAKING_001",
                                                        "message": "존재하지 않는 복약 알림입니다.",
                                                        "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @DeleteMapping
    ResponseEntity<?> deleteNotifiTaking(
            @UserId UUID userId,
            @PathVariable("notifiTakingId") UUID notifiTakingId);


    @Operation(
            summary = "약물 알림 목록 조회",
            description = """
                    특정 환자 약물에 설정된 모든 알림을 조회합니다.
                    
                    **주요 기능:**
                    - 환자 약물에 설정된 전체 알림 목록 조회
                    - 알림 ID, 시간, 활성화 상태 포함
                    - 시간 순으로 정렬된 결과 제공
                    
                    **응답 정보:**
                    - 알림 고유 ID
                    - 설정된 알림 시간 (LocalTime 형식)
                    - 활성화 상태 (true/false)
                    
                    **사용 시나리오:**
                    - 현재 설정된 알림 확인
                    - 알림 수정 전 기존 설정 조회
                    - 약물 상세 정보 조회 시 알림 정보 표시
                    
                    **정렬:**
                    - 알림 시간을 기준으로 오름차순 정렬
                    - 활성/비활성 상태와 관계없이 모든 알림 포함
                    
                    **권한:**
                    - 해당 사용자가 등록한 환자 약물의 알림만 조회 가능
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "알림 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RetrieveNotifiTakingResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "알림 있음",
                                            summary = "설정된 알림 목록",
                                            value = """
                                                    {
                                                        "errorCode": null,
                                                        "message": "SUCCESS",
                                                        "result": {
                                                            "notifiTakingDtoList": [
                                                                {
                                                                    "id": "123e4567-e89b-12d3-a456-426614174000",
                                                                    "time": "08:00:00",
                                                                    "isActive": true
                                                                },
                                                                {
                                                                    "id": "456e7890-e89b-12d3-a456-426614174001",
                                                                    "time": "12:00:00",
                                                                    "isActive": true
                                                                },
                                                                {
                                                                    "id": "789e0123-e89b-12d3-a456-426614174002",
                                                                    "time": "18:00:00",
                                                                    "isActive": false
                                                                }
                                                            ]
                                                        }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "알림 없음",
                                            summary = "설정된 알림이 없는 경우",
                                            value = """
                                                    {
                                                        "errorCode": null,
                                                        "message": "SUCCESS",
                                                        "result": {
                                                            "notifiTakingDtoList": []
                                                        }
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리소스를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "사용자 없음",
                                            summary = "존재하지 않는 사용자",
                                            value = """
                                                    {
                                                       "errorCode": "USER_001",
                                                       "message": "존재하지 않는 사용자입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "환자 약물 없음",
                                            summary = "존재하지 않는 환자 약물 또는 권한 없음",
                                            value = """
                                                    {
                                                       "errorCode": "PATIENT_DRUG_001",
                                                       "message": "존재하지 않는 환자 약물입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @GetMapping
    ResponseEntity<?> getListNotifiTaking(
            @UserId UUID userId,
            @PathVariable("patientDrugId") UUID patientDrugId);


}
