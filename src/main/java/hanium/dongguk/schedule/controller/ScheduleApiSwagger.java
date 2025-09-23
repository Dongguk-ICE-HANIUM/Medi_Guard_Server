package hanium.dongguk.schedule.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.global.dto.PageResponseDto;
import hanium.dongguk.global.dto.ResponseDto;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.schedule.dto.request.SaveScheduleRequestDto;
import hanium.dongguk.schedule.dto.request.VerifyCodeRequestDto;
import hanium.dongguk.schedule.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Schedule", description = "진료 일정 관리 API")
public interface ScheduleApiSwagger {


    @Operation(
            summary = "예정된 진료 저장 api",
            description = """
                    환자가 직접 예정된 진료를 저장합니다
                    후에, 의사 페이지가 나왔을 경우 환자는 직접 추가 X
                    의사가 추가하도록 변경
                    과거날짜는 진료 시간으로 등록할 수 없습니다.
                    등록된 시간 기준 ~ 1시간 이후까지는 중복 등록할 수 없습니다.
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "예정 진료 저장 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    summary = "진료 저장 성공",
                                    value = """
                                            {
                                                "errorCode" : null,
                                                "message" : "OK",
                                                "result" : null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "진료 저장 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonException.class),
                            examples = {
                                    @ExampleObject(
                                            name = "현재 시간보다 이전 시간을 등록했을 경우",
                                            summary = "과거 시간 등록",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_001",
                                                      "message": "진료 예정일은 과거가 될 수 없습니다.",
                                                      "result": null
                                                    }
                                                    """

                                    ),
                                    @ExampleObject(
                                            name = "등록된 시간 1시간 사이에 등록했을 경우",
                                            summary = "중복 등록",
                                            value = """
                                                    {
                                                       "errorCode": "SCHEDULE_002",
                                                       "message": "이전에 등록했던 진료일과 중복됩니다.",
                                                       "result": null
                                                     }
                                                    """
                                    )
                            }
                    )
            ),

    })
    ResponseEntity<Void> saveSchedule(@RequestBody SaveScheduleRequestDto request, @UserId UUID userId);

    @Operation(
            summary = "오늘의 진료 일정 조회 api",
            description = """
                    환자의 가장 최근 진료 일정을 조회합니다.
                    오늘 날짜의 진료가 있는지 확인하고, 없다면 가장 최근 진료 일정을 반환합니다.
                    등록된 진료 일정이 없는 경우 빈 객체를 반환합니다.
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "오늘의 진료 일정 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "오늘 진료 일정이 있는 경우",
                                            summary = "오늘 진료 있음",
                                            value = """
                                                    {
                                                        "errorCode": null,
                                                        "message": "OK",
                                                        "result": {
                                                            "schedule": {
                                                                "scheduleId": "550e8400-e29b-41d4-a716-446655440000",
                                                                "doctorName": "김의사",
                                                                "hospitalName": "동국대학교병원",
                                                                "time": "2024-09-10T14:30:00"
                                                            },
                                                            "isToday": true,
                                                            "isEmpty": false
                                                        }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "오늘 진료 일정이 없고 미래 진료만 있는 경우",
                                            summary = "미래 진료만 있음",
                                            value = """
                                                    {
                                                        "errorCode": null,
                                                        "message": "OK",
                                                        "result": {
                                                            "schedule": {
                                                                "scheduleId": "550e8400-e29b-41d4-a716-446655440000",
                                                                "doctorName": "김의사",
                                                                "hospitalName": "동국대학교병원",
                                                                "time": "2024-09-21T10:00:00"
                                                            },
                                                            "isToday": false,
                                                            "isEmpty": false
                                                        }
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "등록된 진료 일정이 없는 경우",
                                            summary = "진료 일정 없음",
                                            value = """
                                                    {
                                                        "errorCode": null,
                                                        "message": "OK",
                                                        "result": {
                                                            "schedule": null,
                                                            "isToday": false,
                                                            "isEmpty": true
                                                        }
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<GetTodayScheduleResponseDto> getTodaySchedule(@UserId UUID userId);

    @Operation(
            summary = "완료된 진료 일정 목록 조회 api",
            description = """
                    환자의 완료된 진료 일정 목록을 페이징으로 조회합니다.
                    기본 페이지 크기는 4개이며, 페이징 파라미터를 통해 조정 가능합니다.
                    완료된 진료 일정만 조회됩니다.
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "완료된 진료 일정 목록 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "진료 일정이 있는 경우",
                                            summary = "진료 목록 있음",
                                            value = """
                                                    {
                                                       "errorCode": null,
                                                       "message": "SUCCESS",
                                                       "result": {
                                                         "content": [
                                                           {
                                                             "scheduleId": "32942e3a-8edc-11f0-80f6-00155da312b9",
                                                             "doctorName": "김의사",
                                                             "hospitalName": "서울대학교병원",
                                                             "time": "2024-08-25T11:20:00"
                                                           },
                                                           {
                                                             "scheduleId": "329433fa-8edc-11f0-80f6-00155da312b9",
                                                             "doctorName": "김의사",
                                                             "hospitalName": "서울대학교병원",
                                                             "time": "2024-08-22T16:15:00"
                                                           },
                                                           {
                                                             "scheduleId": "329436d3-8edc-11f0-80f6-00155da312b9",
                                                             "doctorName": "김의사",
                                                             "hospitalName": "서울대학교병원",
                                                             "time": "2024-08-20T09:00:00"
                                                           }
                                                         ],
                                                         "currentPage": 2,
                                                         "totalPage": 3,
                                                         "totalElements": 11,
                                                         "hasNext": false
                                                       }
                                                     }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "진료 일정이 없는 경우",
                                            summary = "진료 목록 없음",
                                            value = """
                                                    {
                                                        "errorCode": null,
                                                        "message": "SUCCESS",
                                                        "result": {
                                                          "content": [],
                                                          "currentPage": 0,
                                                          "totalPage": 0,
                                                          "totalElements": 0,
                                                          "hasNext": false
                                                        }
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<PageResponseDto<ScheduleResponseDto>> getScheduleList(@UserId UUID userId,
                                                                         @RequestParam Integer page);

    @Operation(
            summary = "완료된 진료 일정 상세 조회",
            description = """
                    환자의 완료된 일정 상세 조회로
                    의사이름, 병원 이름, 일정 , 증상, 진료 내용 등.. 을 볼 수 있음
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "상세 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "진료 일정 상세",
                                    summary = "진료 일정 상세 보기",
                                    value = """
                                            {
                                              "errorCode": null,
                                              "message": "SUCCESS",
                                              "result": {
                                                "scheduleId": "3293d66e-8edc-11f0-80f6-00155da312b9",
                                                "doctorName": "김의사",
                                                "hospitalName": "서울대학교병원",
                                                "dateTime": "2024-09-11T14:30:00",
                                                "symptom": "두통, 어지러움",
                                                "diagnosis": "편두통",
                                                "guidance": "충분한 휴식과 수분 섭취",
                                                "warning": "정기 검진"
                                              }
                                            }
                                            """
                            )

                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "진료 상세 조회 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonException.class),
                            examples = {
                                    @ExampleObject(
                                            name = "등록된 스케줄을 찾을 수 없는 경우",
                                            summary = "스케줄을 찾을 수 없는 경우",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_003",
                                                      "message": "등록된 진료 예정일을 찾을 수 없습니다.",
                                                      "result": null
                                                    }
                                                    """

                                    ),
                                    @ExampleObject(
                                            name = "완료된 상태의 스케줄이 아닐 경우",
                                            summary = "완료된 상태의 스케줄이 아닐경우",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_005",
                                                      "message": "완료된 진료를 선택해야 합니다.",
                                                      "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
    })
    ResponseEntity<GetScheduleDetailResponseDto> getScheduleDetail(@UserId UUID userId,
                                                                   @PathVariable UUID scheduleId);

    @Operation(
            summary = "진료 시작시 OTP 코드 생성",
            description = """
                    진료 시작시 OTP 코드 생성
                    6자리의 코드가 생성됨
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "오늘의 진료 시작 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "코드 생성",
                                    summary = "코드 생성",
                                    value = """
                                            {
                                              "errorCode": null,
                                              "message": "SUCCESS",
                                              "result": {
                                                "code": "847236"
                                              }
                                            }
                                            """
                            )

                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "진료 상세 조회 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonException.class),
                            examples = {
                                    @ExampleObject(
                                            name = "등록된 스케줄을 찾을 수 없는 경우",
                                            summary = "스케줄을 찾을 수 없는 경우",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_003",
                                                      "message": "등록된 진료 예정일을 찾을 수 없습니다.",
                                                      "result": null
                                                    }
                                                    """

                                    ),
                                    @ExampleObject(
                                            name = "대기중 상태의 스케줄이 아닐 경우",
                                            summary = "대기중 상태의 스케줄이 아닐경우",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_004",
                                                      "message": "대기중인 진료를 선택해야 합니다.",
                                                      "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "대기중 상태의 스케줄이 아닐 경우",
                                            summary = "대기중 상태의 스케줄이 아닐경우",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_006",
                                                      "message": "오늘의 일정의 진료만 시작가능합니다.",
                                                      "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
    })
    ResponseEntity<StartScheduleResponseDto> startSchedule(@UserId UUID userId,
                                                           @PathVariable UUID scheduleId);
    @Operation(
            summary = "의사 코드 인증 API",
            description = """
                    의사가 환자로부터 받은 8자리 코드를 입력하여 진료를 시작합니다.
                    코드 인증이 성공하면 스케줄 상태가 STARTED에서 IN_PROGRESS로 변경됩니다.
                    코드는 일회용이며, 인증 후 자동으로 만료됩니다.
                    """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "코드 인증 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "코드 인증 성공",
                                    summary = "의사 코드 인증 성공",
                                    value = """
                                            {
                                              "errorCode": null,
                                              "message": "SUCCESS",
                                              "result": {
                                                "scheduleId": "550e8400-e29b-41d4-a716-446655440000"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "코드 인증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonException.class),
                            examples = {
                                    @ExampleObject(
                                            name = "유효하지 않거나 만료된 코드",
                                            summary = "잘못된 코드 입력",
                                            value = """
                                                    {
                                                      "errorCode": "REDIS_003",
                                                      "message": "유효하지않거나 만료된 코드입니다.",
                                                      "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "스케줄을 찾을 수 없는 경우",
                                            summary = "스케줄 없음",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_003",
                                                      "message": "등록된 진료 예정일을 찾을 수 없습니다.",
                                                      "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "의사가 해당 스케줄의 담당의가 아닌 경우",
                                            summary = "권한 없는 의사",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_009",
                                                      "message": "해당하는 일자에 대한 의사가 일치하지 않습니다.",
                                                      "result": null
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "스케줄이 STARTED 상태가 아닌 경우",
                                            summary = "잘못된 스케줄 상태",
                                            value = """
                                                    {
                                                      "errorCode": "SCHEDULE_010",
                                                      "message": "시작되지 않은 스케줄입니다.",
                                                      "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommonException.class),
                            examples = {
                                    @ExampleObject(
                                            name = "데이터 역직렬화 실패",
                                            summary = "Redis 데이터 처리 오류",
                                            value = """
                                                    {
                                                      "errorCode": "REDIS_002",
                                                      "message": "데이터 역직렬화에 실패했습니다.",
                                                      "result": null
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<VerifyCodeResponseDto> verifyCode(@UserId UUID userId,
                                                     @RequestBody @Valid VerifyCodeRequestDto request);
}