package hanium.dongguk.notification.taking.service;

import hanium.dongguk.notification.taking.domain.NotifiTakingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotifiTakingRemover {
    private final NotifiTakingRepository notifiTakingRepository;

    public void deleteById(final UUID id) {
        notifiTakingRepository.deleteById(id);
    }
}
