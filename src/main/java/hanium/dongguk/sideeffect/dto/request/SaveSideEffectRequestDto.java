package hanium.dongguk.sideeffect.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaveSideEffectRequestDto(
        @JsonProperty(value = "sideEffectList")
        @NotNull
        @Schema(
                description = "저장할 부작용 목록",
                example = """
    [
        {
            "id": "550e8400-e29b-41d4-a716-446655440000",
            "description": "두통이 심하고 어지러움증이 있습니다. 복용 후 30분 정도 지나면 증상이 나타납니다."
        },
        {
            "id": "550e8400-e29b-41d4-a716-446655440001", 
            "description": "위장 장애와 메스꺼움이 발생했습니다."
        }
    ]
    """
        )
        List<SaveSideEffectDto> saveSideEffectList
) {
}
