package hanium.dongguk.calendar.dto.request;

import java.util.UUID;

public record CalendarDrugSaveDto(
        UUID patientDrugId,
        Integer timeSlot
) {}