package hanium.dongguk.notification.taking.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotifiTakingRepository extends JpaRepository<NotifiTaking, UUID> {
    List<NotifiTaking> findAllByPatientDrugId(UUID patientDrugId);
}
