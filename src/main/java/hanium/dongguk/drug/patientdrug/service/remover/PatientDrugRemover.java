package hanium.dongguk.drug.patientdrug.service.remover;

import hanium.dongguk.drug.patientdrug.domain.PatientDrugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientDrugRemover {
    private final PatientDrugRepository patientDrugRepository;

    public void deleteByIdAndUserId(final UUID id, final UUID userId) {
        patientDrugRepository.deleteByIdAndUserPatientId(id, userId);
    }
}
