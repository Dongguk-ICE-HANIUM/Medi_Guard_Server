package hanium.dongguk.user.patient.service;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.core.exception.UserErrorCode;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.domain.UserPatientRepository;
import hanium.dongguk.user.patient.exception.UserPatientErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPatientRetriever {

    private final UserPatientRepository userPatientRepository;

    UserPatient getUserPatient(UUID userId) {
        return userPatientRepository.findById(userId)
                    .orElseThrow(() -> CommonException.type(UserErrorCode.NOT_FOUND_USER));
    }

    public UserPatient findByUserId(final UUID userId) {
        return userPatientRepository.findById(userId)
                .orElseThrow(() -> CommonException.type(UserPatientErrorCode.NOT_FOUND_USER_PATIENT));
    }
}
