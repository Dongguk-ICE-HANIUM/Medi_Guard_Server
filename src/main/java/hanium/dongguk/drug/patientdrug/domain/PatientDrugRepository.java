package hanium.dongguk.drug.patientdrug.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientDrugRepository extends JpaRepository<PatientDrug, UUID> {
    Optional<PatientDrug> findByIdAndUserPatientId(UUID id, UUID userId);
    void deleteByIdAndUserPatientId(UUID id, UUID userId);
    boolean existsByIdAndUserPatientId(UUID id, UUID userId);
}
