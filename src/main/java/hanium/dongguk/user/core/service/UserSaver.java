package hanium.dongguk.user.core.service;

import hanium.dongguk.user.core.domain.UserRepository;
import hanium.dongguk.user.patient.domain.UserPatient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSaver {

    private final UserRepository userRepository;

    public void save(UserPatient userPatient){
        userRepository.save(userPatient);
    }
}
