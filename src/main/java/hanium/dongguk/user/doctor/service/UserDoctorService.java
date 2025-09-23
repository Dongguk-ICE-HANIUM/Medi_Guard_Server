package hanium.dongguk.user.doctor.service;

import hanium.dongguk.user.doctor.domain.EDepartment;
import hanium.dongguk.user.doctor.domain.UserDoctor;
import hanium.dongguk.user.doctor.dto.DoctorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDoctorService {
    private final UserDoctorRetriever userDoctorRetriever;

    public List<DoctorInfo> searchDoctorList(EDepartment department, String nameKeyWord) {
        List<UserDoctor> doctorList = userDoctorRetriever.searchUserDoctorList(department, nameKeyWord);

        return doctorList.stream().map(DoctorInfo::from).toList();
    }

}
