package hanium.dongguk.sideeffect.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SideEffectRepository extends JpaRepository<SideEffect, UUID> {
    List<SideEffect> findByCalendarDrugCalendarUserPatientId(UUID userPatientId);
    Optional<SideEffect> findByIdAndCalendarDrugCalendarUserPatientId(UUID sideEffectId, UUID patientId);
}