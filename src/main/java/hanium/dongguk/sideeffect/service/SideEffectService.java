package hanium.dongguk.sideeffect.service;

import hanium.dongguk.calendar.domain.CalendarDrug;
import hanium.dongguk.calendar.service.CalendarDrugRetriever;
import hanium.dongguk.sideeffect.domain.SideEffect;
import hanium.dongguk.sideeffect.dto.request.SaveSideEffectRequestDto;
import hanium.dongguk.sideeffect.dto.request.UpdateSideEffectRequestDto;
import hanium.dongguk.sideeffect.dto.response.SideEffectResponseDto;
import hanium.dongguk.sideeffect.dto.response.SideEffectListResponseDto;
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
    private final SideEffectRemover sideEffectRemover;
    private final CalendarDrugRetriever calendarDrugRetriever;
    
    @Transactional
    public void saveSideEffect(UUID patientId, SaveSideEffectRequestDto requestDto) {
        CalendarDrug calendarDrug = calendarDrugRetriever.findById(requestDto.id());

        if (!calendarDrug.getCalendar().getUserPatient().getId().equals(patientId)) {
            throw CommonException.type(SideEffectErrorCode.UNAUTHORIZED_ACCESS);
        }
        
        SideEffect sideEffect = SideEffect.create(
                requestDto.description(),
                calendarDrug
        );
        
        sideEffectSaver.save(sideEffect);
    }
    
    public SideEffectListResponseDto getSideEffect(UUID patientId) {
        List<SideEffect> sideEffects = sideEffectRetriever.findByPatientIdWithPatientDrug(patientId);
        
        List<SideEffectResponseDto> sideEffectResponseDtos = sideEffects.stream()
                .map(sideEffect -> SideEffectResponseDto.of(sideEffect, sideEffect.getCalendarDrug().getPatientDrug()))
                .toList();
        
        return new SideEffectListResponseDto(sideEffectResponseDtos);
    }
    
    @Transactional
    public void updateSideEffect(UUID sideEffectId, UUID patientId, UpdateSideEffectRequestDto requestDto) {
        SideEffect sideEffect = sideEffectRetriever.findByIdAndPatientId(sideEffectId, patientId);
        sideEffect.updateDescription(requestDto.description());
    }
    
    @Transactional
    public void deleteSideEffect(UUID sideEffectId, UUID patientId) {
        SideEffect sideEffect = sideEffectRetriever.findByIdAndPatientId(sideEffectId, patientId);
        sideEffectRemover.remove(sideEffect);
    }
}
