package hanium.dongguk.drug.patientdrug.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticularDateRepository extends JpaRepository<ParticularDate, UUID> {
}
