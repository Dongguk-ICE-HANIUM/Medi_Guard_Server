package hanium.dongguk.drug.patientdrug.service.service;

import hanium.dongguk.drug.patientdrug.domain.DrugGroup;
import hanium.dongguk.drug.patientdrug.dto.request.CreateDrugGroupRequestDto;
import hanium.dongguk.drug.patientdrug.service.saver.DrugGroupSaver;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DrugGroupService {
    private final DrugGroupSaver drugGroupSaver;
    private final UserPatientRetriever userPatientRetriever;

    public URI create(
            UUID userId,
            @Valid CreateDrugGroupRequestDto requestDto) {
        UserPatient currentUserPatient
                = userPatientRetriever.getUserPatient(userId);

        DrugGroup newDrugGroup = DrugGroup.create(
                                        requestDto.name(),
                                        currentUserPatient
                                );
        newDrugGroup = drugGroupSaver.save(newDrugGroup);

        return URI.create(newDrugGroup.getId().toString());
    }
}
