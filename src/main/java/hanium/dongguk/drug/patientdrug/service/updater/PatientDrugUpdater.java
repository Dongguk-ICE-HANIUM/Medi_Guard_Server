package hanium.dongguk.drug.patientdrug.service.updater;

import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientDrugUpdater {
    public void update(PatientDrug target, final Boolean isActive) {
        target.update(isActive);
    }
}
