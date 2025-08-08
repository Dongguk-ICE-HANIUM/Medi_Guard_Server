package hanium.dongguk.calendar.domain;

import hanium.dongguk.patientdrug.domain.PatientDrug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, UUID> {
    Optional<CalendarDrug> findByCalendarAndTimeSlot(Calendar calendar, Integer timeSlot);

    List<CalendarDrug> findByPatientDrug_UserPatient_Id(UUID patientId);

    Optional<CalendarDrug> findByCalendarAndPatientDrug(Calendar calendar, PatientDrug patientDrug); // 선택적으로
}
