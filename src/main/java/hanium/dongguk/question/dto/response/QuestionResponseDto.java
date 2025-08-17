package hanium.dongguk.question.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.question.domain.Question;
import hanium.dongguk.question.dto.QuestionDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record QuestionResponseDto(
        @JsonProperty(value = "questionList")
        @Schema(description = "질문 목록")
        List<QuestionDto> questionList
) {
    public static QuestionResponseDto of(List<Question> questions) {
        return new QuestionResponseDto(
                questions.stream()
                        .map(QuestionDto::of)
                        .toList()
        );
    }
}