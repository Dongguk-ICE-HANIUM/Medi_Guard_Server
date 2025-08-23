package hanium.dongguk.drug.patientdrug.controller;

import hanium.dongguk.drug.patientdrug.dto.request.CreateDrugGroupRequestDto;
import hanium.dongguk.drug.patientdrug.service.service.DrugGroupService;
import hanium.dongguk.global.annotation.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drug-group")
public class DrugGroupController implements DrugGroupApiSwagger {
    private final DrugGroupService drugGroupService;

    @PostMapping
    public ResponseEntity<?> createDrugGroup(
            @UserId UUID userId,
            @Valid @RequestBody CreateDrugGroupRequestDto requestDto) {
        return ResponseEntity.created(
                drugGroupService.create(userId, requestDto)
        ).build();
    }

    @GetMapping
    public ResponseEntity<?> getListDrugDroup(@UserId UUID userId) {
        return ResponseEntity.ok(
                drugGroupService.getListDrugGroup(userId)
        );
    }
}
