package hanium.dongguk.user.doctor.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SearchDoctorListResponseDto(
        @JsonProperty("doctorInfoList")
        List<DoctorInfoDto> doctorInfoList
) {
    public static SearchDoctorListResponseDto from(List<DoctorInfoDto> doctorInfoDtoList) {
        return new SearchDoctorListResponseDto(doctorInfoDtoList);
    }
}
