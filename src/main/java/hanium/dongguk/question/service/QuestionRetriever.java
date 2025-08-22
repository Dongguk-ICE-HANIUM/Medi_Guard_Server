package hanium.dongguk.question.service;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.question.domain.Question;
import hanium.dongguk.question.domain.QuestionRepository;
import hanium.dongguk.question.exception.QuestionErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuestionRetriever {

    private final QuestionRepository questionRepository;

    public List<Question> findByCalendarIdAndPatientId(UUID calendarId, UUID patientId) {
        return questionRepository.findByCalendarIdAndUserPatientId(calendarId, patientId);
    }

    public Optional<Question> findById(UUID questionId) {
        return questionRepository.findById(questionId);
    }

    public Question findByIdOrThrow(UUID questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> CommonException.type(QuestionErrorCode.QUESTION_NOT_FOUND));
    }
}