package hanium.dongguk.user.core.service;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.core.domain.Email;
import hanium.dongguk.user.core.domain.UserRepository;
import hanium.dongguk.user.core.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRetriever {

    private final UserRepository userRepository;

    public boolean validateEmailNotExist(Email email) {
        return userRepository.existsByEmail(email);
    }

}
