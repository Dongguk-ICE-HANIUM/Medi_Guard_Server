package hanium.dongguk.auth.controller;

import hanium.dongguk.auth.provider.apple.dto.AppleLoginRequestDto;
import hanium.dongguk.global.dto.JwtDto;
import hanium.dongguk.auth.provider.google.dto.GoogleLoginRequestDto;
import hanium.dongguk.auth.dto.SocialLoginSignupRequestDto;
import hanium.dongguk.auth.dto.SocialLoginResponseDto;
import hanium.dongguk.auth.dto.NormalRegisterRequestDto;
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
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "사용자 인증 API")
public interface AuthApiSwagger {

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
    ResponseEntity<Void> normalRegister(
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


    @Operation(
            summary = "구글 소셜 로그인",
            description = """
                    구글 OAuth를 통한 소셜 로그인을 처리합니다.
                    
                    **주요 기능:**
                    - 구글 액세스 토큰으로 사용자 정보 조회
                    - 신규 사용자인 경우 PENDING 상태로 임시 계정 생성
                    - 기존 사용자인 경우 JWT 토큰 발급
                    - 비활성화된 계정 자동 복구
                    
                    **응답 분기:**
                    - 신규 사용자: `isSignUpNeeded=true`, `jwtDto=null`
                    - 기존 사용자: `isSignUpNeeded=false`, JWT 토큰 발급
                    - 비활성화된 기존 사용자: 계정 활성화 후 JWT 토큰 발급
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 처리 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "신규 사용자 (회원가입 필요)",
                                            summary = "첫 로그인으로 회원가입이 필요한 경우",
                                            value = """
                                                    {
                                                        "jwtDto": null,
                                                        "isSignUpNeeded": true,
                                                        "userId": "123e4567-e89b-12d3-a456-426614174000"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "기존 사용자 (로그인 완료)",
                                            summary = "이미 가입된 사용자의 로그인 완료",
                                            value = """
                                                    {
                                                        "jwtDto": {
                                                            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                                            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                                                        },
                                                        "isSignUpNeeded": false,
                                                        "userId": "123e4567-e89b-12d3-a456-426614174000"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "구글 OAuth 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "구글 OAuth 실패",
                                            summary = "잘못된 액세스 토큰",
                                            value = """
                                                    {
                                                       "errorCode": "USER_008",
                                                       "message": "구글 사용자 정보 조회 실패",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "구글 사용자 정보 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "사용자 정보 없음",
                                    summary = "구글에서 사용자 정보를 찾을 수 없음",
                                    value = """
                                            {
                                               "errorCode": "USER_007",
                                               "message": "구글 사용자 정보를 찾을 수 없습니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<SocialLoginResponseDto> googleLogin(
            @Parameter(
                    description = "구글 로그인 요청 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GoogleLoginRequestDto.class),
                            examples = @ExampleObject(
                                    name = "구글 로그인 요청 예시",
                                    summary = "구글에서 받은 액세스 토큰",
                                    value = """
                                            {
                                                "accessToken": "ya29.a0ARrdaM_example_google_access_token"
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody GoogleLoginRequestDto googleLoginRequestDto
    );

    @Operation(
            summary = "소셜 로그인 회원가입 완료",
            description = """
                    구글 소셜 로그인 후 추가 정보 입력으로 회원가입을 완료합니다.
                    
                    **주요 기능:**
                    - PENDING 상태 사용자의 추가 정보 입력
                    - 임산부 필수 정보 등록 (생년월일, 신체정보, 임신정보)
                    - 사용자 상태를 ACTIVE로 변경
                    - JWT 토큰 발급
                    
                    **검증 규칙:**
                    - 사용자 ID: 존재하고 PENDING 상태여야 함
                    - 이름: 필수 입력값
                    - 키: 100~220cm 범위
                    - 몸무게: 35~150kg 범위
                    - 임신주차: 0~50주 범위
                    - 생년월일: 과거 날짜, 14~100세 범위
                    - 출산예정일: 미래 날짜
                    - 날짜 형식: yyyy-MM-dd
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 완료 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "회원가입 완료",
                                    summary = "소셜 회원가입 완료 후 JWT 토큰 발급",
                                    value = """
                                            {
                                                "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                                "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
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
                                            name = "이미 가입 완료",
                                            summary = "이미 회원가입이 완료된 사용자",
                                            value = """
                                                    {
                                                       "errorCode": "USER_009",
                                                       "message": "이미 회원가입이 완료된 사용자입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "생년월일 오류",
                                            summary = "유효하지 않은 생년월일",
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
                                                            "height": "키는 100cm 이상 220cm 이하여야 합니다",
                                                            "weight": "몸무게는 35kg 이상 150kg 이하여야 합니다",
                                                            "pregnancyWeek": "임신 주차는 0주 이상 50주 이하여야 합니다",
                                                            "birthday": "날짜 형식은 yyyy-MM-dd 이어야 합니다",
                                                            "dueDate": "날짜 형식은 yyyy-MM-dd 이어야 합니다"
                                                        }
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "사용자 없음",
                                    summary = "존재하지 않는 사용자 ID",
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
    ResponseEntity<JwtDto> socialLoginSignup(
            @Valid @RequestBody SocialLoginSignupRequestDto request
    );

    @Operation(
            summary = "애플 소셜 로그인",
            description = """
                    애플 OAuth를 통한 소셜 로그인을 처리합니다.
                    
                    **주요 기능:**
                    - 애플 ID 토큰 검증 및 사용자 정보 추출
                    - 신규 사용자인 경우 PENDING 상태로 임시 계정 생성
                    - 기존 사용자인 경우 JWT 토큰 발급
                    - 비활성화된 계정 자동 복구
                    
                    **처리 과정:**
                    1. Apple의 공개 키를 사용하여 ID 토큰 검증
                    2. 토큰에서 사용자 정보 (이메일, 이름) 추출
                    3. 이메일로 기존 사용자 여부 확인
                    4. 신규 사용자인 경우 임시 계정 생성, 기존 사용자인 경우 로그인 처리
                    
                    **응답 분기:**
                    - 신규 사용자: `isSignUpNeeded=true`, `jwtDto=null`
                    - 기존 사용자: `isSignUpNeeded=false`, JWT 토큰 발급
                    - 비활성화된 기존 사용자: 계정 활성화 후 JWT 토큰 발급
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 처리 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "신규 사용자 (회원가입 필요)",
                                            summary = "첫 로그인으로 회원가입이 필요한 경우",
                                            value = """
                                                    {
                                                        "jwtDto": null,
                                                        "isSignUpNeeded": true,
                                                        "userId": "123e4567-e89b-12d3-a456-426614174000"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "기존 사용자 (로그인 완료)",
                                            summary = "이미 가입된 사용자의 로그인 완료",
                                            value = """
                                                    {
                                                        "jwtDto": {
                                                            "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                                            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                                                        },
                                                        "isSignUpNeeded": false,
                                                        "userId": "123e4567-e89b-12d3-a456-426614174000"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "JSON 처리 실패",
                                            summary = "JSON 직렬화/역직렬화 오류",
                                            value = """
                                                    {
                                                       "errorCode": "AUTH_001",
                                                       "message": "JSON을 직렬화 혹은 역직렬화 할 수 없습니다.",
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
                                    name = "공개키 매칭 실패",
                                    summary = "적절한 공개키를 찾을 수 없음",
                                    value = """
                                            {
                                               "errorCode": "AUTH_003",
                                               "message": "적절한 공개키를 찾을 수 없습니다.",
                                               "result": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "처리할 수 없는 요청",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "지원하지 않는 알고리즘",
                                            summary = "지원하지 않는 공개키 알고리즘",
                                            value = """
                                                    {
                                                       "errorCode": "AUTH_005",
                                                       "message": "지원하지 않는 공개키 알고리즘입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Spring Security 미지원 알고리즘",
                                            summary = "Spring Security에서 지원하지 않는 알고리즘",
                                            value = """
                                                    {
                                                       "errorCode": "AUTH_006",
                                                       "message": "Spring Security에서 지원하지 않는 공개키 알고리즘입니다.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "외부 서비스 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "애플 토큰 요청 실패",
                                            summary = "애플 서버로의 토큰 요청 실패",
                                            value = """
                                                    {
                                                       "errorCode": "AUTH_002",
                                                       "message": "apple token 요청에 실패했습니다.",
                                                       "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "애플 공개키 요청 실패",
                                            summary = "애플 공개키 조회 실패",
                                            value = """
                                                    {
                                                       "errorCode": "AUTH_004",
                                                       "message": "public key 요청에 실패했습니다.",
                                                       "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<SocialLoginResponseDto> appleLogin(
            @Valid @RequestBody AppleLoginRequestDto request
    );
}
