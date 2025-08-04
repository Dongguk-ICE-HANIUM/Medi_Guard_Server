package hanium.dongguk.calendar.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CalendarRepository extends JpaRepository<Calendar, UUID> {
    Optional<Calendar> findByDate(LocalDate date);
}
