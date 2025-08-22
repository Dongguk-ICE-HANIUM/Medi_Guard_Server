package hanium.dongguk.drug.patientdrug.controller;

import hanium.dongguk.drug.patientdrug.dto.request.CreatePatientDrugRequestDto;
import hanium.dongguk.drug.patientdrug.dto.request.PatchPatientDrugIsEssentialRequestDto;
import hanium.dongguk.drug.patientdrug.dto.response.RetrievePatientDrugResponseDto;
import hanium.dongguk.global.annotation.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "PatientDrug", description = "환자 약물 API")
public interface PatientDrugApiSwagger {
    @Operation(
            summary = "환자 약물 등록",
            description = """
                    환자의 복용 약물을 등록합니다.
                    
                    **주요 기능:**
                    - 약물 정보 및 복용 스케줄 등록
                    - 복용 타입별 세부 설정 (매일, 특정일 간격, 특정 요일, 특정 날짜, 필요시 복용)
                    - 약물 그룹 연결 (선택사항)
                    - 복용 기간 및 용량 설정
                    
                    **복용 타입 설명:**
                    - EVERY_DAY: 매일 복용
                    - PARTICULAR_INTERVAL: 특정일 간격으로 복용 (takingInterval 필요)
                    - PARTICULAR_DAY: 특정 요일에 복용 (takingInterval 필요)
                    - SPECIFIC_DATE: 특정 날짜에만 복용 (specificDateList 필요)
                    - NEED: 필요시 복용
                    
                    **검증 규칙:**
                    - 약물 ID: 필수, 존재하는 약물이어야 함
                    - 약물 이름: 필수, 공백 불가
                    - 복용 시작일/종료일: 필수, 시작일이 종료일보다 이후일 수 없음
                    - 복용량: 필수, 0보다 큰 값
                    - 복용 주기: 필수, 0 이상
                    - 하루 복용 횟수: 0 이상
                    - 특정일 리스트: SPECIFIC_DATE 타입 시 필수 (비어있으면 안됨)
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "약물 등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "약물 등록 완료",
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
                    description = "잘못된 요청 - 입력값 검증 실패",
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
                                                            "drugId": "약물 ID가 누락되었습니다.",
                                                            "name": "약물 이름이 누락되었거나 비어있습니다.",
                                                            "startAt": "복용 시작 날짜가 누락되었습니다.",
                                                            "endAt": "복용 종료 날짜가 누락되었습니다.",
                                                            "amount": "1회 복용량이 누락되었습니다.",
                                                            "takingInterval": "interval이 누락되었습니다."
                                                        }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "복용 기간 오류",
                                            summary = "시작일이 종료일보다 늦은 경우",
                                            value = """
                                                    {
                                                        "success": false,
                                                        "message": "입력값이 올바르지 않습니다",
                                                        "errors": {
                                                            "isStartBeforeOrEqualToEnd": "유효하지 않은 복용 기간입니다."
                                                        }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "특정일 오류",
                                            summary = "SPECIFIC_DATE 타입에서 특정일 리스트가 비어있는 경우",
                                            value = """
                                                    {
                                                       "errorCode": "TAKING_TYPE_003",
                                                       "message": "유효하지 않은 특정일입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "복용 방법 오류",
                                            summary = "유효하지 않은 복용 타입",
                                            value = """
                                                    {
                                                       "errorCode": "TAKING_TYPE_002",
                                                       "message": "유효하지 않은 복용 방법입니다.",
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
                                            name = "약물 없음",
                                            summary = "존재하지 않는 약물 ID",
                                            value = """
                                                    {
                                                       "errorCode": "DRUG_001",
                                                       "message": "존재하지 않는 약물입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "복용 방법 없음",
                                            summary = "존재하지 않는 복용 방법",
                                            value = """
                                                    {
                                                       "errorCode": "TAKING_TYPE_001",
                                                       "message": "존재하지 않는 복용 방법입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    @PostMapping
    public ResponseEntity<?> createPatientDrug(
            @Parameter(
                    description = "환자 약물 등록 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatePatientDrugRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "매일 복용 약물 등록",
                                            summary = "매일 복용하는 약물 등록 예시",
                                            value = """
                                                    {
                                                        "drugId": "123e4567-e89b-12d3-a456-426614174000",
                                                        "name": "타이레놀",
                                                        "startAt": "2025-01-01",
                                                        "endAt": "2025-01-31",
                                                        "takingType": "EVERY_DAY",
                                                        "interval": 0,
                                                        "specificDateList": [],
                                                        "perDay": 3,
                                                        "amount": 500.0,
                                                        "groupId": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "특정일 간격 복용",
                                            summary = "3일 간격으로 복용하는 약물 등록 예시",
                                            value = """
                                                    {
                                                        "drugId": "123e4567-e89b-12d3-a456-426614174000",
                                                        "name": "비타민 D",
                                                        "startAt": "2025-01-01",
                                                        "endAt": "2025-03-31",
                                                        "takingType": "PARTICULAR_INTERVAL",
                                                        "interval": 3,
                                                        "specificDateList": [],
                                                        "perDay": 1,
                                                        "amount": 1000.0,
                                                        "groupId": "987f6543-d21c-43b2-9876-543210987654"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "특정 날짜 복용",
                                            summary = "지정된 날짜에만 복용하는 약물 등록 예시",
                                            value = """
                                                    {
                                                        "drugId": "123e4567-e89b-12d3-a456-426614174000",
                                                        "name": "항생제",
                                                        "startAt": "2025-01-01",
                                                        "endAt": "2025-01-10",
                                                        "takingType": "SPECIFIC_DATE",
                                                        "interval": 0,
                                                        "specificDateList": ["2025-01-01", "2025-01-03", "2025-01-05", "2025-01-07", "2025-01-09"],
                                                        "perDay": 2,
                                                        "amount": 250.0,
                                                        "groupId": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "필요시 복용",
                                            summary = "필요할 때만 복용하는 약물 등록 예시",
                                            value = """
                                                    {
                                                        "drugId": "123e4567-e89b-12d3-a456-426614174000",
                                                        "name": "진통제",
                                                        "startAt": "2025-01-01",
                                                        "endAt": "2025-12-31",
                                                        "takingType": "NEED",
                                                        "interval": 0,
                                                        "specificDateList": [],
                                                        "perDay": 0,
                                                        "amount": 500.0,
                                                        "groupId": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @UserId UUID userId,
            @Valid @RequestBody CreatePatientDrugRequestDto requestDto);

    @Operation(
            summary = "환자 약물 활성화 상태 변경",
            description = """
                    환자 약물의 활성화 상태를 변경합니다.
                    
                    **주요 기능:**
                    - 환자 약물의 활성화/비활성화 상태 토글
                    - 복용 중단 또는 재개 시 사용
                    - 약물 삭제 없이 일시적 중단 관리
                    
                    **사용 시나리오:**
                    - 의사 지시에 따른 복용 중단/재개
                    - 부작용으로 인한 일시적 중단
                    - 복용 스케줄 관리
                    
                    **검증 규칙:**
                    - 환자 약물 ID: 존재하고 해당 사용자의 약물이어야 함
                    - isActive: 필수 입력값 (true/false)
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "활성화 상태 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "상태 변경 완료",
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
                    description = "잘못된 요청 - 입력값 검증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "필드 검증 오류",
                                    summary = "isActive 필드 누락",
                                    value = """
                                            {
                                                "success": false,
                                                "message": "입력값이 올바르지 않습니다",
                                                "errors": {
                                                    "isActive": "보류(isActive) 여부가 누락되었습니다."
                                                }
                                            }
                                            """
                            )
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
    @PatchMapping("/{patientDrugId}")
    public ResponseEntity<?> patchIsActive(
            @Parameter(description = "사용자 ID", hidden = true)
            @UserId UUID userId,
            @Parameter(
                    description = "환자 약물 ID", 
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("patientDrugId")
            UUID patientDrugId,
            @Parameter(
                    description = "활성화 상태 변경 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PatchPatientDrugIsEssentialRequestDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "활성화",
                                            summary = "약물 복용 재개",
                                            value = """
                                                    {
                                                        "isActive": true
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "비활성화",
                                            summary = "약물 복용 중단",
                                            value = """
                                                    {
                                                        "isActive": false
                                                    }
                                                    """
                                    )
                            }
                    )
            )
            @Valid @RequestBody
            PatchPatientDrugIsEssentialRequestDto requestDto);

    @Operation(
            summary = "환자 약물 상세 조회",
            description = """
                    환자 약물의 상세 정보를 조회합니다.
                    
                    **주요 기능:**
                    - 환자 약물의 전체 상세 정보 조회
                    - 약물 기본 정보 (효능, 경고사항, 부작용, 상호작용, 보관방법)
                    - 복용 스케줄 정보 (시작일, 종료일, 복용 타입, 복용 횟수, 복용량)
                    - 활성화 상태 및 필수 약물 여부
                    - 연결된 약물 그룹 정보
                    - 복용 알림 시간 리스트
                    
                    **응답 정보:**
                    - 약물 코드, 효능, 경고사항, 부작용, 상호작용, 보관방법
                    - 복용 일정 및 용량 정보
                    - 그룹 연결 정보 (있는 경우)
                    - 설정된 알림 시간들
                    
                    **권한:**
                    - 해당 사용자가 등록한 약물만 조회 가능
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "환자 약물 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RetrievePatientDrugResponseDto.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "환자 약물 상세 정보",
                                    value = """
                                            {
                                                "id": "123e4567-e89b-12d3-a456-426614174000",
                                                "name": "타이레놀",
                                                "code": "A123456789",
                                                "effect": "해열, 진통",
                                                "warning": "간질환 환자 주의",
                                                "sideEffect": "드물게 피부발진, 소화불량",
                                                "interaction": "와파린과 병용 시 출혈 위험 증가",
                                                "depositMethod": "실온보관, 직사광선 피함",
                                                "startAt": "2025-01-01",
                                                "endAt": "2025-01-31",
                                                "takingType": "EVERY_DAY",
                                                "perDay": 3,
                                                "amount": 500.0,
                                                "isActive": true,
                                                "isEssential": false,
                                                "groupName": "감기약",
                                                "groupId": "987f6543-d21c-43b2-9876-543210987654",
                                                "notifiTakingList": [
                                                    {
                                                        "id": "111e1111-e11b-11d1-a111-111111111111",
                                                        "time": "08:00:00"
                                                    },
                                                    {
                                                        "id": "222e2222-e22b-22d2-a222-222222222222", 
                                                        "time": "13:00:00"
                                                    },
                                                    {
                                                        "id": "333e3333-e33b-33d3-a333-333333333333",
                                                        "time": "18:00:00"
                                                    }
                                                ]
                                            }
                                            """
                            )
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
    @GetMapping("/{patientDrugId}")
    public ResponseEntity<?> retrievePatientDrug(
            @Parameter(description = "사용자 ID", hidden = true)
            @UserId UUID userId,
            @Parameter(
                    description = "환자 약물 ID", 
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("patientDrugId") UUID patientDrugId
    );

    @Operation(
            summary = "환자 약물 삭제",
            description = """
                    환자 약물을 완전히 삭제합니다.
                    
                    **주요 기능:**
                    - 환자 약물 정보 완전 삭제
                    - 연관된 복용 알림 정보도 함께 삭제
                    - 복용 기록 및 스케줄 모두 제거
                    - 되돌릴 수 없는 영구 삭제
                    
                    **주의사항:**
                    - 삭제된 약물 정보는 복구할 수 없습니다
                    - 일시적 중단이 목적이라면 활성화 상태 변경(PATCH)을 사용하세요
                    - 해당 사용자가 등록한 약물만 삭제 가능합니다
                    
                    **삭제되는 정보:**
                    - 약물 기본 정보 및 복용 스케줄
                    - 설정된 모든 복용 알림
                    - 약물 그룹 연결 정보
                    - 복용 타입 및 특정일 설정
                    
                    **권한:**
                    - 해당 사용자가 등록한 약물만 삭제 가능
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "약물 삭제 성공 (No Content)",
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
    @DeleteMapping("/{patientDrugId}")
    public ResponseEntity<?> deletePatientDrug(
            @Parameter(description = "사용자 ID", hidden = true)
            @UserId UUID userId,
            @Parameter(
                    description = "삭제할 환자 약물 ID", 
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("patientDrugId") UUID patientDrugId
    );

    @Operation(
            summary = "환자 약물에서 약물 그룹 연결 해제",
            description = """
                    환자 약물에서 약물 그룹과의 연결을 해제합니다.
                    
                    **주요 기능:**
                    - 환자 약물과 약물 그룹 간의 연결 해제
                    - 약물 자체는 유지되며, 그룹 소속만 제거
                    - 그룹 단위 관리에서 개별 약물 관리로 전환
                    - 약물 그룹이 연결되어 있지 않은 경우에도 안전하게 처리
                    
                    **사용 시나리오:**
                    - 복용 중인 약물을 그룹에서 분리하여 개별 관리
                    - 처방 변경으로 인한 그룹 해제
                    - 복용 패턴 변경으로 인한 개별 관리 필요
                    
                    **처리 결과:**
                    - 약물의 groupId가 null로 변경
                    - 약물의 다른 정보는 모두 유지
                    - 그룹이 없던 약물의 경우 아무 변화 없이 정상 처리
                    
                    **권한:**
                    - 해당 사용자가 등록한 약물만 처리 가능
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "약물 그룹 연결 해제 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "그룹 연결 해제 완료",
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
    @PatchMapping("/{patientDrugId}/release")
    public ResponseEntity<?> detachDrugGoupFromPatientDrug(
            @Parameter(description = "사용자 ID", hidden = true)
            @UserId UUID userId,
            @Parameter(
                    description = "그룹 연결을 해제할 환자 약물 ID", 
                    required = true,
                    example = "123e4567-e89b-12d3-a456-426614174000"
            )
            @PathVariable("patientDrugId") UUID patientDrugId
    );
}
