package hanium.dongguk.question.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.question.dto.response.QuestionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateQuestionListRequestDto(
        @JsonProperty(value = "questionList")
        @NotNull
        @Schema(
                description = "수정할 질문 목록",
                example = """
    [
        {
            "type": "MEDICATION_COMPLIANCE",
            "answer": "약물 복용을 잊어버린 날이 있었습니다."
        },
        {
            "type": "MEDICATION_SIDE_EFFECTS",
            "answer": "가벼운 메스꺼움이 있었습니다."
        },
        {
            "type": "PHYSICAL_SYMPTOMS",
            "answer": "증상이 이전보다 악화된 것 같습니다."
        }
    ]
    """
        )
        List<UpdateQuestionDto> updateQuestionList
) {
}
