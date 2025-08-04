package hanium.dongguk.user.core.controller;

import hanium.dongguk.user.core.dto.request.NormalRegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "사용자 관리 API")
public interface UserApi {

    @Operation(
            summary = "일반 회원가입",
            description = """
                    임산부 사용자의 일반 회원가입을 처리합니다.
                    
                    **주요 기능:**
                    - 이메일 중복 검증
                    - 비밀번호 암호화 저장
                    - 생년월일 및 출산예정일 유효성 검증
                    - 임신 정보 등록
                    
                    **검증 규칙:**
                    - 이메일: 올바른 이메일 형식이어야 합니다
                    - 비밀번호: 필수 입력값입니다
                    - 이름: 필수 입력값입니다
                    - 키: 100~250cm 범위여야 합니다
                    - 몸무게: 30~200kg 범위여야 합니다
                    - 임신주차: 0~50주 범위여야 합니다
                    - 생년월일: 과거 날짜여야 합니다
                    - 출산예정일: 미래 날짜여야 합니다
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "회원가입 완료",
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
                                            name = "이메일 중복",
                                            summary = "이미 가입된 이메일",
                                            value = """
                                                    {
                                                       "errorCode": "USER_005",
                                                       "message": "이미 존재하는 이메일입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "이메일 형식 오류",
                                            summary = "잘못된 이메일 형식",
                                            value = """
                                                    {
                                                       "result": null,
                                                       "errorCode": "REQUEST_001",
                                                       "message": "잘못된 요청입니다."
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "생년월일 오류",
                                            summary = "미래 날짜 생년월일",
                                            value = """
                                                    {
                                                       "errorCode": "USER_004",
                                                       "message": "유효하지 않은 생년월일입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "출산예정일 오류",
                                            summary = "과거 날짜 출산예정일",
                                            value = """
                                                    {
                                                      "errorCode": "USER_006",
                                                      "message": "출산 예정일은 과거가 될 수 없습니다.",
                                                      "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "필드 검증 오류",
                                            summary = "필수 필드 누락 또는 범위 초과",
                                            value = """
                                                    {
                                                        "success": false,
                                                        "message": "입력값이 올바르지 않습니다",
                                                        "errors": {
                                                            "height": "키는 130cm 이상 200cm 이하여야 합니다",
                                                            "weight": "몸무게는 35kg 이상 150kg 이하여야 합니다",
                                                            "pregnancyWeek": "임신 주차는 0주 이상 50주 이하여야 합니다"
                                                        }
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    void normalRegister(
            @Parameter(
                    description = "일반 회원가입 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NormalRegisterRequestDto.class),
                            examples = @ExampleObject(
                                    name = "회원가입 요청 예시",
                                    summary = "정상적인 회원가입 요청",
                                    value = """
                                            {
                                                "email": {
                                                    "email": "wpsxkehf7@naver.com"
                                                },
                                                "password": "tkddbs3535",
                                                "name": "홍길동",
                                                "birthday": "2003-03-03",
                                                "height": 180,
                                                "weight": 80,
                                                "dueDate": "2025-08-24",
                                                "pregnancyWeek": 24,
                                                "feeding": false
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody NormalRegisterRequestDto request
    );
}
