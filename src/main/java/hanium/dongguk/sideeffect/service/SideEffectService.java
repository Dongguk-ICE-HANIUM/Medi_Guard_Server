package hanium.dongguk.sideeffect.service;

import hanium.dongguk.calendar.domain.CalendarDrug;
import hanium.dongguk.calendar.domain.CalendarDrugRepository;
import hanium.dongguk.sideeffect.domain.SideEffect;
import hanium.dongguk.sideeffect.domain.SideEffectRepository;
import hanium.dongguk.sideeffect.dto.request.SaveSideEffectRequestDto;
import hanium.dongguk.sideeffect.dto.request.SaveSideEffectDto;
import hanium.dongguk.sideeffect.dto.request.UpdateSideEffectRequestDto;
import hanium.dongguk.sideeffect.dto.request.UpdateSideEffectDto;
import hanium.dongguk.sideeffect.dto.response.SideEffectDto;
import hanium.dongguk.sideeffect.dto.response.SideEffectResponseDto;
import hanium.dongguk.sideeffect.exception.SideEffectErrorCode;
import hanium.dongguk.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SideEffectService {
    
    private final SideEffectSaver sideEffectSaver;
    private final SideEffectRetriever sideEffectRetriever;
    private final SideEffectRepository sideEffectRepository;
    private final CalendarDrugRepository calendarDrugRepository;
    
    @Transactional
    public void saveSideEffects(UUID patientId, SaveSideEffectRequestDto requestDto) {
        for (SaveSideEffectDto sideEffectDto : requestDto.saveSideEffectList()) {
            CalendarDrug calendarDrug = calendarDrugRepository.findById(sideEffectDto.id())
                    .orElseThrow(() -> CommonException.type(SideEffectErrorCode.CALENDAR_DRUG_NOT_FOUND));

            if (!calendarDrug.getCalendar().getUserPatient().getId().equals(patientId)) {
                throw CommonException.type(SideEffectErrorCode.UNAUTHORIZED_ACCESS);
            }
            
            SideEffect sideEffect = SideEffect.create(
                    sideEffectDto.description(),
                    calendarDrug
            );
            
            sideEffectSaver.save(sideEffect);
        }
    }
    
    public SideEffectResponseDto getSideEffects(UUID patientId) {
        List<SideEffect> sideEffects = sideEffectRetriever.findByPatientId(patientId);
        
        return SideEffectResponseDto.from(sideEffects);
    }
    
    @Transactional
    public void updateSideEffect(UUID sideEffectId, UUID patientId, UpdateSideEffectRequestDto requestDto) {
        // 권한 확인 및 부작용 조회
        SideEffect sideEffect = sideEffectRepository.findByIdAndCalendarDrugCalendarUserPatientId(sideEffectId, patientId)
                .orElseThrow(() -> CommonException.type(SideEffectErrorCode.SIDE_EFFECT_NOT_FOUND));
        
        // 설명 업데이트
        sideEffect.updateDescription(requestDto.description());
    }
    
    @Transactional
    public void deleteSideEffect(UUID sideEffectId, UUID patientId) {
        // 권한 확인 및 부작용 조회
        SideEffect sideEffect = sideEffectRepository.findByIdAndCalendarDrugCalendarUserPatientId(sideEffectId, patientId)
                .orElseThrow(() -> CommonException.type(SideEffectErrorCode.SIDE_EFFECT_NOT_FOUND));
        
        // 부작용 삭제
        sideEffectRepository.delete(sideEffect);
    }
}
