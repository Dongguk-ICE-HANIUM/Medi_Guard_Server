package hanium.dongguk.user.doctor.validator;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.doctor.exception.UserDoctorErrorCode;
import hanium.dongguk.user.doctor.service.UserDoctorRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDoctorValidator {

    private final UserDoctorRetriever userDoctorRetriever;

    public void validateDoctor(UUID doctorId){
        if(!userDoctorRetriever.existsDoctor(doctorId)){
            throw CommonException.type(UserDoctorErrorCode.NOT_FOUND_USER_DOCTOR);
        }
    }
}
