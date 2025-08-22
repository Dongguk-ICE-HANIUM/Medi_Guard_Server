package hanium.dongguk.drug.patientdrug.controller;

import hanium.dongguk.drug.patientdrug.dto.request.CreatePatientDrugRequestDto;
import hanium.dongguk.drug.patientdrug.dto.request.PatchPatientDrugIsEssentialRequestDto;
import hanium.dongguk.drug.patientdrug.service.service.PatientDrugService;
import hanium.dongguk.global.annotation.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{patientDrugId}")
    public ResponseEntity<?> patchIsActive(
            @UserId UUID userId,
            @PathVariable("patientDrugId")
            UUID patientDrugId,
            @Valid @RequestBody
            PatchPatientDrugIsEssentialRequestDto requestDto) {
        return ResponseEntity.ok(
                patientDrugService.patchIsActive(
                        userId,
                        patientDrugId,
                        requestDto
                )
        );
    }

    @GetMapping("/{patientDrugId}")
    public ResponseEntity<?> retrievePatientDrug(
            @UserId UUID userId,
            @PathVariable("patientDrugId") UUID patientDrugId
    ) {
        return ResponseEntity.ok(
                patientDrugService.retrieve(userId, patientDrugId)
        );
    }

    @DeleteMapping("/{patientDrugId}")
    public ResponseEntity<?> deletePatientDrug(
            @UserId UUID userId,
            @PathVariable("patientDrugId") UUID patientDrugId
    ) {
        patientDrugService.delete(userId, patientDrugId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{patientDrugId}/release")
    public ResponseEntity<?> detachDrugGoupFromPatientDrug(
            @UserId UUID userId,
            @PathVariable("patientDrugId") UUID patientDrugId
    ) {
        return ResponseEntity.ok(
                patientDrugService.detachDrugGroupFromPatientDrug(
                        userId,
                        patientDrugId
                )
        );
    }
}
