package hanium.dongguk.drug.patientdrug.service.retriever;

import hanium.dongguk.drug.patientdrug.domain.DrugGroup;
import hanium.dongguk.drug.patientdrug.domain.DrugGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DrugGroupRetriever {
    private final DrugGroupRepository drugGroupRepository;

    public Optional<DrugGroup> findOptionalById(final UUID id) {
        return drugGroupRepository.findById(id);
    }

    public List<DrugGroup> findAllByUserPatientId(final UUID patientId) {
        return drugGroupRepository.findAllByUserPatientId(patientId);
    }
}
