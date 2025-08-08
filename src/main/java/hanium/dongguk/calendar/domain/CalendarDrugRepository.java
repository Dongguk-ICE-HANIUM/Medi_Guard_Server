package hanium.dongguk.calendar.domain;

import hanium.dongguk.patientdrug.domain.PatientDrug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface CalendarDrugRepository extends JpaRepository<CalendarDrug, UUID> {
    List<CalendarDrug> findByCalendar(Calendar calendar);

    Optional<CalendarDrug> findByCalendarAndTimeSlot(Calendar calendar, Integer timeSlot);

    List<CalendarDrug> findByPatientDrug_UserPatient_Id(UUID patientId);

    Optional<CalendarDrug> findByCalendarAndPatientDrug(Calendar calendar, PatientDrug patientDrug);
}