package hanium.dongguk.question.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByCalendarIdAndCalendarUserPatientId(UUID calendarId, UUID userPatientId);
}