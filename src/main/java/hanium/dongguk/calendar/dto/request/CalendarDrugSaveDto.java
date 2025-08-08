package hanium.dongguk.calendar.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public record CalendarDrugSaveDto(
        @NotNull UUID patientDrugId,
        @NotNull Integer timeSlot
) {}