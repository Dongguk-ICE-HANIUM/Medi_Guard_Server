package hanium.dongguk.drug.patientdrug.service.service;

import hanium.dongguk.drug.patientdrug.domain.ParticularDate;
import hanium.dongguk.drug.patientdrug.domain.ParticularDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ParticularDateRetriever {

    private final ParticularDateRepository particularDateRepository;

    public List<LocalDate> getParticularDateList(final UUID takingTypeId) {
        List<ParticularDate> particularDateList = particularDateRepository.findByTakingTypeId(takingTypeId);

        return particularDateList.stream()
                                 .map(ParticularDate::getDate)
                                 .toList();
    }
}
