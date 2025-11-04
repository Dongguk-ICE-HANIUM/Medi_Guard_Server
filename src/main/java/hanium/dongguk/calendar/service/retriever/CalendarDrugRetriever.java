package hanium.dongguk.calendar.service.retriever;

import hanium.dongguk.calendar.domain.CalendarDrug;
import hanium.dongguk.calendar.domain.CalendarDrugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CalendarDrugRetriever {
    private final CalendarDrugRepository calendarDrugRepository;

    public List<CalendarDrug> findAllByUserPatientIdAndRecordDateBetween(
            final UUID userPatientId,
            final LocalDate startDate,
            final LocalDate endDate) {
        return calendarDrugRepository
                .findAllByUserPatientIdAndRecordDateBetween(
                        userPatientId,
                        startDate,
                        endDate
                );
    }
}
