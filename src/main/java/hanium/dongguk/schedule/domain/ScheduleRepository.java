package hanium.dongguk.schedule.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findByPatientIdAndScheduleTimeBetween(
            UUID patientId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );
}
