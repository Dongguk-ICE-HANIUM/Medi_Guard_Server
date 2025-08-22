package hanium.dongguk.drug.patientdrug.service.saver;

import hanium.dongguk.drug.patientdrug.domain.TakingType;
import hanium.dongguk.drug.patientdrug.domain.TakingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TakingTypeSaver {
    private final TakingTypeRepository takingTypeRepository;

    public TakingType save(final TakingType target) {
        return takingTypeRepository.save(target);
    }
}
