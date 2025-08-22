package hanium.dongguk.drug.patientdrug.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DrugGroupRepository extends JpaRepository<DrugGroup, UUID> {
    Optional<DrugGroup> findById(UUID id);
}
