package hanium.dongguk.user.doctor.controller;

import hanium.dongguk.global.dto.ResponseDto;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.doctor.domain.EDepartment;
import hanium.dongguk.user.doctor.dto.response.DoctorInfoDto;
import hanium.dongguk.user.doctor.dto.response.SearchDoctorListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "UserDoctor", description = "의사 관련 API")
public interface UserDoctorApiSwagger {

    @Operation(
            summary = "의사 검색 API",
            description = """
                진료과별 의사 검색 기능입니다.
                진료과는 필수이며, 의사 이름은 선택사항입니다.
                의사 이름을 입력하지 않으면 해당 진료과의 모든 의사를 반환합니다.
                """
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "의사 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "검색 결과가 있는 경우",
                                            summary = "의사 목록 반환",
                                            value = """
                                                    {
                                                      "errorCode": null,
                                                      "message": "SUCCESS",
                                                      "result": {
                                                        "doctorInfoList": [
                                                          {
                                                            "doctorId": "5cf71052-2c9c-4721-8b87-7aa8f7ed1379",
                                                            "name": "송민교",
                                                            "hospitalName": "동국대병원",
                                                            "department": "INTERNAL_MEDICINE"
                                                          },
                                                          {
                                                            "doctorId": "88541408-b9f7-40e0-931f-4a9a682bd2b3",
                                                            "name": "추상윤",
                                                            "hospitalName": "동국대병원",
                                                            "department": "INTERNAL_MEDICINE"
                                                          },
                                                          {
                                                            "doctorId": "e454ffa7-d1a1-4936-9ab4-84cb4462f04b",
                                                            "name": "추상윤",
                                                            "hospitalName": "동국대병원",
                                                            "department": "INTERNAL_MEDICINE"
                                                          }
                                                        ]
                                                      }
                                                    }
                                                """
                                    ),
                                    @ExampleObject(
                                            name = "검색 결과가 없는 경우",
                                            summary = "빈 목록 반환",
                                            value = """
                                                {
                                                  "errorCode": null,
                                                  "message": "SUCCESS",
                                                  "result": []
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
                            schema = @Schema(implementation = CommonException.class),
                            examples = @ExampleObject(
                                    name = "유효하지 않은 진료과",
                                    summary = "진료과 Enum 값 오류",
                                    value = """
                                        {
                                          "errorCode": "REQUEST_012",
                                          "message": "Enum 타입으로 변경할 수 없습니다.",
                                          "result": null
                                        }
                                        """
                            )
                    )
            )
    })
    ResponseEntity<SearchDoctorListResponseDto> searchDoctorList(
            @RequestParam EDepartment department,
            @RequestParam(required = false) String nameKeyWord
    );
}
