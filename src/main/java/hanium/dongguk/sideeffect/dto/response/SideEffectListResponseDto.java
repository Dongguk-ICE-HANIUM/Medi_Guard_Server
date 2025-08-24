package hanium.dongguk.sideeffect.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import hanium.dongguk.sideeffect.domain.SideEffect;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "부작용 목록 응답 DTO")
public record SideEffectListResponseDto(
        @JsonProperty("sideEffectList")
        @Schema(description = "부작용 목록")
        List<SideEffectResponseDto> sideEffectList
) {
    public static SideEffectListResponseDto from(List<SideEffect> sideEffects) {
        return new SideEffectListResponseDto(
                sideEffects.stream()
                        .map(SideEffectResponseDto::from)
                        .toList()
        );
    }

    public static SideEffectListResponseDto empty() {
        return new SideEffectListResponseDto(null);
    }
}
