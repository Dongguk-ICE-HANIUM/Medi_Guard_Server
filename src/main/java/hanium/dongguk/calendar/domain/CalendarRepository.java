package hanium.dongguk.calendar.domain;

import hanium.dongguk.user.patient.UserPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CalendarRepository extends JpaRepository<Calendar, UUID> {
    // 정말 필요한 경우만 Calendar 자체로 조회하는 메서드 정의
    Optional<Calendar> findByDateAndUserPatient(LocalDate date, UserPatient userPatient);
}