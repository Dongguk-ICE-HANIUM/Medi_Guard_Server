package hanium.dongguk.calendar.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CalendarDrugRepository extends JpaRepository<CalendarDrug, UUID> {
//    List<CalendarDrug> findAllByPatientDrugIdAndRecordDateBetween(
//            UUID patientDrugId,
//            LocalDate startDate,
//            LocalDate endDate);

    @Query(
            value = """
            SELECT cd.*
            FROM calendar_drug cd
            JOIN patient_drug pd ON cd.patient_drug_id = pd.id
            WHERE pd.user_patient_id = :userPatientId
              AND cd.record_date BETWEEN :startDate AND :endDate
              """,
            nativeQuery = true)
    List<CalendarDrug> findAllByUserPatientIdAndRecordDateBetween(
            UUID userPatientId,
            LocalDate startDate,
            LocalDate endDate);
}