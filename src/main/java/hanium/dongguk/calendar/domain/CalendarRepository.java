package hanium.dongguk.calendar.domain;

import hanium.dongguk.user.patient.domain.UserPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CalendarRepository extends JpaRepository<Calendar, UUID> {
    Optional<Calendar> findByDateAndUserPatientId(LocalDate date, UUID userPatientId);
    Optional<Calendar> findByIdAndUserPatientId(UUID id, UUID userPatientId);

}