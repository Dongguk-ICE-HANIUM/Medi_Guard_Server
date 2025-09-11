package hanium.dongguk.user.doctor.service;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.doctor.domain.UserDoctor;
import hanium.dongguk.user.doctor.domain.UserDoctorRepository;
import hanium.dongguk.user.doctor.exception.UserDoctorErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDoctorRetriever {

    private final UserDoctorRepository userDoctorRepository;

    public UserDoctor getUserDoctor (UUID doctorId){
        return userDoctorRepository.findById(doctorId)
                .orElseThrow(() -> CommonException.type(UserDoctorErrorCode.NOT_FOUND_USER_DOCTOR));
    }
}
