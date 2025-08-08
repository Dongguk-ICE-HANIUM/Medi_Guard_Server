package hanium.dongguk.user.core.service;

import hanium.dongguk.user.core.domain.Email;
import hanium.dongguk.user.core.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRetriever {

    private final UserRepository userRepository;

    public boolean existsByEmail(Email email) {
        return userRepository.existsByEmail(email);
    }

}
