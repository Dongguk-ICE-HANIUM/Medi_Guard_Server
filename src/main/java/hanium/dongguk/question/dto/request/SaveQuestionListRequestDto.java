package hanium.dongguk.question.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaveQuestionListRequestDto(
        @JsonProperty(value = "questionList")
        @NotNull
        @Schema(
                description = "저장할 질문 목록",
                example = """
    [
        {
            "type": "MEDICATION_COMPLIANCE",
            "answer": "네, 매일 규칙적으로 복용하고 있습니다."
        },
        {
            "type": "MEDICATION_SIDE_EFFECTS",
            "answer": "특별한 부작용은 없습니다."
        },
        {
            "type": "PHYSICAL_SYMPTOMS",
            "answer": "증상이 많이 호전되었습니다."
        }
    ]
    """
        )
        List<SaveQuestionDto> saveQuestionList
) {
}