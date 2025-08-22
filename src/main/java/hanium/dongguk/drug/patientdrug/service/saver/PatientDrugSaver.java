package hanium.dongguk.drug.patientdrug.service.saver;

import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.drug.patientdrug.domain.PatientDrugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientDrugSaver {
    private final PatientDrugRepository patientDrugRepository;

    public PatientDrug save(final PatientDrug target) {
        return patientDrugRepository.save(target);
    }
}
