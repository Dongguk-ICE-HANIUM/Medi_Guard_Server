package hanium.dongguk.sideeffect.service;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.sideeffect.domain.SideEffect;
import hanium.dongguk.sideeffect.domain.SideEffectRepository;
import hanium.dongguk.sideeffect.exception.SideEffectErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SideEffectRetriever {
    
    private final SideEffectRepository sideEffectRepository;
    
    public List<SideEffect> findByPatientIdWithPatientDrug(UUID patientId) {
        return sideEffectRepository.findByPatientIdWithPatientDrug(patientId);
    }
    
    public SideEffect findByIdAndPatientId(UUID sideEffectId, UUID patientId) {
        return sideEffectRepository.findByIdAndCalendarDrugCalendarUserPatientId(sideEffectId, patientId)
                .orElseThrow(() -> CommonException.type(SideEffectErrorCode.SIDE_EFFECT_NOT_FOUND));
    }
}
