package hanium.dongguk.drug.patientdrug.service.saver;

import hanium.dongguk.drug.patientdrug.domain.ParticularDate;
import hanium.dongguk.drug.patientdrug.domain.ParticularDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticularDateSaver {
    private final ParticularDateRepository particularDateRepository;

    public void saveAll(final List<ParticularDate> target) {
        particularDateRepository.saveAll(target);
    }
}
