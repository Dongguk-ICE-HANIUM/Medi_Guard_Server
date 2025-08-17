package hanium.dongguk.question.service;

import hanium.dongguk.question.domain.Question;
import hanium.dongguk.question.domain.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionSaver {

    private final QuestionRepository questionRepository;

    public List<Question> saveAll(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }
}