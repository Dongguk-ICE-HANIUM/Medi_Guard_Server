package hanium.dongguk.calendar.service;

import hanium.dongguk.calendar.domain.CalendarDrug;
import hanium.dongguk.calendar.domain.CalendarDrugRepository;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.sideeffect.exception.SideEffectErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CalendarDrugRetriever {
    
    private final CalendarDrugRepository calendarDrugRepository;
    
    public CalendarDrug findById(UUID id) {
        return calendarDrugRepository.findById(id)
                .orElseThrow(() -> CommonException.type(SideEffectErrorCode.CALENDAR_DRUG_NOT_FOUND));
    }
}