package hanium.dongguk.notification.taking.service;

import hanium.dongguk.notification.taking.domain.NotifiTaking;
import hanium.dongguk.notification.taking.domain.NotifiTakingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotifiTakingSaver {
    private final NotifiTakingRepository notifiTakingRepository;

    public NotifiTaking save(NotifiTaking notifiTaking) {
        return notifiTakingRepository.save(notifiTaking);
    }
}
