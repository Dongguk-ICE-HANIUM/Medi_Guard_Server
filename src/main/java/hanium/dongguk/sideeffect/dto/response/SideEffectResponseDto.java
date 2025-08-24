package hanium.dongguk.sideeffect.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.sideeffect.domain.SideEffect;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "부작용 목록 응답 DTO")
public record SideEffectResponseDto(
        @JsonProperty("sideEffectList")
        @Schema(description = "부작용 목록")
        List<SideEffectDto> sideEffectList
) {
    public static SideEffectResponseDto from(List<SideEffect> sideEffects) {
        return new SideEffectResponseDto(
                sideEffects.stream()
                        .map(SideEffectDto::from)
                        .toList()
        );
    }

    public static SideEffectResponseDto empty() {
        return new SideEffectResponseDto(null);
    }
}
