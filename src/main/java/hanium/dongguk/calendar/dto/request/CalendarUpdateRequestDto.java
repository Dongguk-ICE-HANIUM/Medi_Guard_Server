package hanium.dongguk.calendar.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "캘린더 수정 요청 DTO")
public record CalendarUpdateRequestDto(
        @JsonProperty("description")
        @NotBlank(message = "설명은 필수입니다.")
        @Schema(description = "기분 설명", example = "기분이 많이 좋아졌습니다.")
        String description,
        
        @JsonProperty("emotion")
        @NotNull(message = "감정은 필수입니다.")
        @Schema(description = "감정 상태", example = "HAPPY")
        EEmotion emotion,
        
        @JsonProperty("questionType")
        @NotNull(message = "질문 유형은 필수입니다.")
        @Schema(description = "질문 유형", example = "PHYSICAL_SYMPTOMS")
        EQuestionType questionType
) {
}