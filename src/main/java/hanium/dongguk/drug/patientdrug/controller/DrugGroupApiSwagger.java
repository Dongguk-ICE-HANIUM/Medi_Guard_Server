package hanium.dongguk.drug.patientdrug.controller;


import hanium.dongguk.drug.patientdrug.dto.request.CreateDrugGroupRequestDto;
import hanium.dongguk.global.annotation.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "DrugGroup", description = "환자 약물 그룹 API")
public interface DrugGroupApiSwagger {

    @Operation(
            summary = "환자 약물 그룹 생성",
            description = """
                    환자의 새로운 약물 그룹을 생성합니다.
                    
                    **주요 기능:**
                    - 사용자가 약물을 그룹화하여 관리할 수 있는 그룹 생성
                    - 복용하는 약물들을 목적이나 시간대별로 분류 가능
                    - 그룹 단위로 약물 관리 및 알림 설정 가능
                    
                    **사용 시나리오:**
                    - 아침/점심/저녁 시간대별 그룹 생성
                    - 용도별 그룹 생성 (혈압약, 당뇨약 등)
                    - 증상별 그룹 생성
                    
                    **처리 결과:**
                    - 새로운 약물 그룹이 생성되고 고유 ID 반환
                    - 생성된 그룹에 약물들을 추가하여 관리 가능
                    
                    **권한:**
                    - 로그인한 사용자만 자신의 약물 그룹 생성 가능
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "약물 그룹 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "생성 성공",
                                    summary = "약물 그룹 생성 완료",
                                    value = """
                                            HTTP/1.1 201 Created
                                            Location: /api/drug-group/123e4567-e89b-12d3-a456-426614174000
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "유효성 검증 실패",
                                    summary = "그룹 이름이 비어있는 경우",
                                    value = """
                                            {
                                               "errorCode": "VALIDATION_ERROR",
                                               "message": "그룹 이름이 비어있거나 누락되었습니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "사용자 없음",
                                    summary = "존재하지 않는 사용자",
                                    value = """
                                            {
                                               "errorCode": "USER_001",
                                               "message": "존재하지 않는 사용자입니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<?> createDrugGroup(
            @Parameter(description = "사용자 ID", hidden = true)
            @UserId UUID userId,
            @Parameter(
                    description = "약물 그룹 생성 정보", 
                    required = true
            )
            @Valid @RequestBody CreateDrugGroupRequestDto requestDto);

    @Operation(
            summary = "환자 약물 그룹 목록 조회",
            description = """
                    환자의 모든 약물 그룹 목록을 조회합니다.
                    
                    **주요 기능:**
                    - 사용자가 생성한 모든 약물 그룹 목록 반환
                    - 활성화된 그룹만 조회
                    - 그룹별 기본 정보 제공
                    
                    **사용 시나리오:**
                    - 약물 그룹 관리 페이지에서 목록 표시
                    - 약물 추가 시 그룹 선택 옵션 제공
                    - 그룹별 약물 현황 확인
                    
                    **권한:**
                    - 로그인한 사용자만 자신의 약물 그룹 목록 조회 가능
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "약물 그룹 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "조회 성공",
                                    summary = "약물 그룹 목록 반환",
                                    value = """
                                            {
                                                "errorCode": null,
                                                "message": "SUCCESS",
                                                "result": {
                                                    "drugGroups": [
                                                        {
                                                            "id": "123e4567-e89b-12d3-a456-426614174000",
                                                            "name": "아침 복용약",
                                                        },
                                                        {
                                                            "id": "987e6543-e21c-34f5-b678-987654321000",
                                                            "name": "저녁 복용약",
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
                    description = "사용자를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "사용자 없음",
                                    summary = "존재하지 않는 사용자",
                                    value = """
                                            {
                                               "errorCode": "USER_001",
                                               "message": "존재하지 않는 사용자입니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            )
    })
    public ResponseEntity<?> getListDrugGroup(
            @Parameter(description = "사용자 ID", hidden = true)
            @UserId UUID userId);

}
