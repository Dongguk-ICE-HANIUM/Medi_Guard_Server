package hanium.dongguk.user.patient.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPatientRepository extends JpaRepository<UserPatient, UUID> {
    Optional<UserPatient> findById(UUID userId);
}
