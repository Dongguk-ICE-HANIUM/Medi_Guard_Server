package hanium.dongguk.drug.patientdrug.service.retriever;

import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.drug.patientdrug.domain.PatientDrugRepository;
import hanium.dongguk.drug.patientdrug.exception.PatientDrugErrorCode;
import hanium.dongguk.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientDrugRetriever {
    private final PatientDrugRepository patientDrugRepository;

    public PatientDrug findById(final UUID id) {
        return patientDrugRepository.findById(id)
                .orElseThrow(() -> CommonException.type(PatientDrugErrorCode.NOT_FOUND));
    }

    public PatientDrug findByIdAndUserId(final UUID id, final UUID userId) {
       return patientDrugRepository.findByIdAndUserPatientId(id, userId)
               .orElseThrow(() -> CommonException.type(PatientDrugErrorCode.NOT_FOUND));
    }

}
