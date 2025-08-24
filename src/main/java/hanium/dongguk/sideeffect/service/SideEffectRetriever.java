package hanium.dongguk.sideeffect.service;

import hanium.dongguk.sideeffect.domain.SideEffect;
import hanium.dongguk.sideeffect.domain.SideEffectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SideEffectRetriever {
    
    private final SideEffectRepository sideEffectRepository;
    
    public List<SideEffect> findByPatientId(UUID patientId) {
        return sideEffectRepository.findByCalendarDrugCalendarUserPatientId(patientId);
    }
}
