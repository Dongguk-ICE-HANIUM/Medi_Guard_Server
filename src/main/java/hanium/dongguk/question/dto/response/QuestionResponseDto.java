package hanium.dongguk.question.dto.response;

import hanium.dongguk.question.domain.EQuestionType;
import hanium.dongguk.question.domain.Question;

import java.util.List;
import java.util.UUID;

public record QuestionResponseDto(
        List<QuestionItemDto> questionList
) {
    public record QuestionItemDto(
            UUID id,
            EQuestionType type,
            String answer
    ) {
        public static QuestionItemDto of(Question question) {
            return new QuestionItemDto(
                    question.getId(),
                    question.getType(),
                    question.getAnswer()
            );
        }
    }

    public static QuestionResponseDto of(List<Question> questions) {
        return new QuestionResponseDto(
                questions.stream()
                        .map(QuestionItemDto::of)
                        .toList()
        );
    }
}