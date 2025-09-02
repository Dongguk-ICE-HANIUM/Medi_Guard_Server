package hanium.dongguk.sideeffect.service;

import hanium.dongguk.sideeffect.domain.SideEffect;
import hanium.dongguk.sideeffect.domain.SideEffectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SideEffectRemover {
    
    private final SideEffectRepository sideEffectRepository;
    
    public void remove(SideEffect sideEffect) {
        sideEffectRepository.delete(sideEffect);
    }
}