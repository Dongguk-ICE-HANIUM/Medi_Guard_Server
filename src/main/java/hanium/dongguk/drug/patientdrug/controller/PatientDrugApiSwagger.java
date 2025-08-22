package hanium.dongguk.drug.patientdrug.controller;

import hanium.dongguk.drug.patientdrug.dto.request.CreatePatientDrugRequestDto;
import hanium.dongguk.global.annotation.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "PatientDrug", description = "환자 약물 API")
public interface PatientDrugApiSwagger {
    @Operation(
            summary = "환자 약물 등록",
            description = """
                    환자 약물을 등록합니다.
                    """
    )
    @PostMapping
    public ResponseEntity<?> createPatientDrug(
            @UserId UUID userId,
            @Valid @RequestBody CreatePatientDrugRequestDto requestDto);

}
