package hanium.dongguk.calendar.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CalendarDrugRepository extends JpaRepository<CalendarDrug, UUID> {
    List<CalendarDrug> findByCalendar(Calendar calendar);
}