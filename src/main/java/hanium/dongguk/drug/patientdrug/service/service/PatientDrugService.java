package hanium.dongguk.drug.patientdrug.service.service;

import hanium.dongguk.drug.drug.domain.Drug;
import hanium.dongguk.drug.drug.service.DrugRetriever;
import hanium.dongguk.drug.patientdrug.domain.*;
import hanium.dongguk.drug.patientdrug.dto.request.CreatePatientDrugRequestDto;
import hanium.dongguk.drug.patientdrug.exception.TakingTypeErrorCode;
import hanium.dongguk.drug.patientdrug.service.retriever.DrugGroupRetriever;
import hanium.dongguk.drug.patientdrug.service.saver.ParticularDateSaver;
import hanium.dongguk.drug.patientdrug.service.saver.PatientDrugSaver;
import hanium.dongguk.drug.patientdrug.service.saver.TakingTypeSaver;
import hanium.dongguk.drug.patientdrug.util.TakingTypeFactory;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientDrugService {

    private final PatientDrugSaver patientDrugSaver;
    private final UserPatientRetriever userPatientRetriever;
    private final DrugRetriever drugRetriever;
    private final DrugGroupRetriever drugGroupRetriever;
    private final TakingTypeSaver takingTypeSaver;
    private final ParticularDateSaver particularDateSaver;

    @Transactional
    public URI createPatientDrug(
            UUID userId,
            CreatePatientDrugRequestDto requestDto) {
        UserPatient currentUserPatient = userPatientRetriever.findByUserId(userId);
        Drug targetDrug = drugRetriever.findById(requestDto.drugId());

        TakingType newTakingType
                = takingTypeSaver.save( // DB
                        TakingTypeFactory.classifyFromETakingType(
                                requestDto.takingType(),
                                requestDto.takingInterval()
                        )
                );
        if (newTakingType.getType() == ETakingType.SPECIFIC_DATE) {
            Set<LocalDate> uniqueDateSet = new HashSet<>(requestDto.requestSpecificDateList());
            if (uniqueDateSet.isEmpty()) {
                throw CommonException.type(TakingTypeErrorCode.INVALID_SPECIFIC_DATE);
            }

            List<ParticularDate> particularDateList = uniqueDateSet.stream()
                    .map(date -> ParticularDate.create(date, newTakingType))
                    .toList();
            particularDateSaver.saveAll(particularDateList); // DB
        }

        UUID groupId = requestDto.groupId();
        DrugGroup drugGroup = (groupId != null)
                ? drugGroupRetriever.findOptionalById(groupId).orElse(null)
                : null;

        PatientDrug newPatientDrug
                = patientDrugSaver.save(
                        PatientDrug.create(
                                requestDto.name(),
                                requestDto.startAt(),
                                requestDto.endAt(),
                                requestDto.perDay(),
                                requestDto.amount(),
                                "",
                                currentUserPatient,
                                targetDrug,
                                newTakingType,
                                drugGroup
                        )
                );

        return URI.create(newPatientDrug.getId().toString());
    }
}
