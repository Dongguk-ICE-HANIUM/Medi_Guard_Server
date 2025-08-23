package hanium.dongguk.drug.patientdrug.service.saver;

import hanium.dongguk.drug.patientdrug.domain.DrugGroup;
import hanium.dongguk.drug.patientdrug.domain.DrugGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DrugGroupSaver {
    private final DrugGroupRepository drugGroupRepository;

    public DrugGroup save(DrugGroup target) {
        return drugGroupRepository.save(target);
    }
}
