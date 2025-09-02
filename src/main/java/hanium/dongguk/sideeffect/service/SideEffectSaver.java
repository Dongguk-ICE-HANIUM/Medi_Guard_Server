package hanium.dongguk.sideeffect.service;

import hanium.dongguk.sideeffect.domain.SideEffect;
import hanium.dongguk.sideeffect.domain.SideEffectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SideEffectSaver {
    
    private final SideEffectRepository sideEffectRepository;
    
    public SideEffect save(SideEffect sideEffect) {
        return sideEffectRepository.save(sideEffect);
    }
}
