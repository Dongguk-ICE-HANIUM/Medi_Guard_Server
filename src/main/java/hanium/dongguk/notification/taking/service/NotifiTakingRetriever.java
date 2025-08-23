package hanium.dongguk.notification.taking.service;

import hanium.dongguk.notification.taking.domain.NotifiTaking;
import hanium.dongguk.notification.taking.domain.NotifiTakingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotifiTakingRetriever {
    private final NotifiTakingRepository notifiTakingRepository;

    public List<NotifiTaking> findAllByPatientDrugId(final UUID patientDrugId) {
        return notifiTakingRepository.findAllByPatientDrugId(patientDrugId);
    }

    public List<NotifiTaking> findAllByIdInAndPatientDrugId(final List<UUID> idList, final UUID patientDrugId) {
       return notifiTakingRepository.findAllByIdInAndPatientDrugId(idList, patientDrugId);
    }
}
