package hanium.dongguk.sideeffect.service;

import hanium.dongguk.calendar.domain.CalendarDrug;
import hanium.dongguk.calendar.domain.CalendarDrugRepository;
import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.drug.patientdrug.service.retriever.PatientDrugRetriever;
import hanium.dongguk.sideeffect.domain.SideEffect;
import hanium.dongguk.sideeffect.domain.SideEffectRepository;
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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SideEffectService {
    
    private final SideEffectSaver sideEffectSaver;
    private final SideEffectRetriever sideEffectRetriever;
    private final SideEffectRepository sideEffectRepository;
    private final CalendarDrugRepository calendarDrugRepository;
    private final PatientDrugRetriever patientDrugRetriever;
    
    @Transactional
    public void saveSideEffect(UUID patientId, SaveSideEffectRequestDto requestDto) {
        CalendarDrug calendarDrug = calendarDrugRepository.findById(requestDto.id())
                .orElseThrow(() -> CommonException.type(SideEffectErrorCode.CALENDAR_DRUG_NOT_FOUND));

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
        List<SideEffect> sideEffects = sideEffectRetriever.findByPatientId(patientId);
        
        List<UUID> patientDrugIds = sideEffects.stream()
                .map(sideEffect -> sideEffect.getCalendarDrug().getPatientDrug().getId())
                .distinct()
                .toList();
        
        Map<UUID, PatientDrug> patientDrugMap = patientDrugIds.stream()
                .map(patientDrugRetriever::findById)
                .collect(Collectors.toMap(PatientDrug::getId, patientDrug -> patientDrug));
        
        List<SideEffectResponseDto> sideEffectResponseDtos = sideEffects.stream()
                .map(sideEffect -> {
                    UUID patientDrugId = sideEffect.getCalendarDrug().getPatientDrug().getId();
                    PatientDrug patientDrug = patientDrugMap.get(patientDrugId);
                    return SideEffectResponseDto.of(sideEffect, patientDrug);
                })
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
        sideEffectRepository.delete(sideEffect);
    }
}
