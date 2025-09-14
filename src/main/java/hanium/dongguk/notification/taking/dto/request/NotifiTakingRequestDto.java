package hanium.dongguk.notification.taking.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "약물 알림 요청 정보")
public record NotifiTakingRequestDto(
        @Schema(
                description = "약물 알림 리스트", 
                required = true,
                example = """
                        [
                            {
                                "id": "123e4567-e89b-12d3-a456-426614174000",
                                "time": "08:00:00",
                                "isActive": true
                            },
                            {
                                "time": "12:00:00",
                                "isActive": true
                            },
                            {
                                "time": "18:00:00",
                                "isActive": false
                            }
                        ]
                        """
        )
        @NotNull(message = "약물 알림 리스트가 누락되었습니다.")
        @JsonProperty("notifiTakingList")
        List<NotifiTakingDto> notifiTakingDtoList
) {
}
