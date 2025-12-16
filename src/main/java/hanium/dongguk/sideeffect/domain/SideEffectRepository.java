package hanium.dongguk.sideeffect.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SideEffectRepository extends JpaRepository<SideEffect, UUID> {
    List<SideEffect> findByCalendarDrugCalendarUserPatientId(UUID userPatientId);
    Optional<SideEffect> findByIdAndCalendarDrugCalendarUserPatientId(UUID sideEffectId, UUID patientId);
    
    @Query("SELECT se FROM SideEffect se " +
           "JOIN FETCH se.calendarDrug cd " +
           "JOIN FETCH cd.patientDrug pd " +
           "WHERE cd.calendar.userPatient.id = :patientId")
    List<SideEffect> findByPatientIdWithPatientDrug(@Param("patientId") UUID patientId);

    @Query(value = """
       SELECT s.calendar_Drug_id
       FROM SideEffect s
       WHERE s.calendar_Drug_id IN :calendarDrugIdSet
    """,
    nativeQuery = true)
    List<UUID> findCalendarDrugIdsWithSideEffects(@Param("calendarDrugIdSet") Set<UUID> calendarDrugIdSet);
}