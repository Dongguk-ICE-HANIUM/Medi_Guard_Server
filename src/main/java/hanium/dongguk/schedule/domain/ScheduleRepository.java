package hanium.dongguk.schedule.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.patient.id = :patientId AND s.scheduleTime BETWEEN :startTime AND :endTime")
    boolean existsByPatientIdAndScheduleTimeBetween(
            @Param("patientId") UUID patientId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    Optional<Schedule> findTopByPatientIdAndStatusAndScheduleTimeGreaterThanEqualOrderByScheduleTimeAsc(
            UUID patientId,
            EScheduleStatus status,
            LocalDateTime now
    );

    Page<Schedule> findByPatientIdAndStatusOrderByScheduleTimeDesc(UUID patientId,
                                                                   EScheduleStatus status,
                                                                   Pageable pageable);

    Optional<Schedule> findByPatientIdAndId(UUID patientId, UUID scheduleId);
}
