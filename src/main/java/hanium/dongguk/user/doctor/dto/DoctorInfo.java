package hanium.dongguk.user.doctor.dto;

import hanium.dongguk.user.doctor.domain.EDepartment;
import hanium.dongguk.user.doctor.domain.UserDoctor;

import java.util.UUID;

public record DoctorInfo(

        UUID doctorId,

        String name,

        String hospitalName,

        EDepartment department
) {
    public static DoctorInfo from (UserDoctor userDoctor){
        return new DoctorInfo(userDoctor.getId(),
                userDoctor.getName(),
                userDoctor.getHospitalName(),
                userDoctor.getDepartment());
    }
}
