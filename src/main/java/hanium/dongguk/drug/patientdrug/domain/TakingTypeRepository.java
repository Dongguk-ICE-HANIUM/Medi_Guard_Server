package hanium.dongguk.drug.patientdrug.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TakingTypeRepository extends JpaRepository<TakingType, UUID> {
}
