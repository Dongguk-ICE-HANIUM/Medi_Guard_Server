package hanium.dongguk.user.doctor.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.user.doctor.domain.EDepartment;
import hanium.dongguk.user.doctor.domain.UserDoctor;

import java.util.UUID;

public record DoctorInfoDto(

        @JsonProperty("doctorId")
        UUID doctorId,

        @JsonProperty("name")
        String name,

        @JsonProperty("hospitalName")
        String hospitalName,

        @JsonProperty("department")
        EDepartment department
) {
    public static DoctorInfoDto from (UserDoctor userDoctor){
        return new DoctorInfoDto(userDoctor.getId(),
                userDoctor.getName(),
                userDoctor.getHospitalName(),
                userDoctor.getDepartment());
    }
}
