package hanium.dongguk.drug.patientdrug.controller;

import hanium.dongguk.drug.patientdrug.dto.request.CreatePatientDrugRequestDto;
import hanium.dongguk.drug.patientdrug.service.service.PatientDrugService;
import hanium.dongguk.global.annotation.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient-drug")
public class PatientDrugController implements PatientDrugApiSwagger {
    private final PatientDrugService patientDrugService;

    @PostMapping
    public ResponseEntity<?> createPatientDrug(
            @UserId UUID userId,
            @Valid @RequestBody CreatePatientDrugRequestDto requestDto) {
        return ResponseEntity.created(
                patientDrugService.createPatientDrug(userId, requestDto)
        ).build();
    }
}
