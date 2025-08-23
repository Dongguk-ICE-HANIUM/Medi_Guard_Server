package hanium.dongguk.notification.taking.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.notification.taking.dto.request.NotifiTakingRequestDto;
import hanium.dongguk.notification.taking.service.NotifiTakingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotifiTakingController implements NotifiTakingApiSwagger {
    private final NotifiTakingService notifiTakingService;

    @PostMapping("/patient-drug/{patientDrugId}/notifi-taking")
    public ResponseEntity<?> createNotifiTaking(
            @UserId UUID userId,
            @PathVariable("patientDrugId") UUID patientDrugId,
            @Valid @RequestBody NotifiTakingRequestDto requestDto) {
        return ResponseEntity.created(
                notifiTakingService.create(userId, patientDrugId, requestDto)
        ).build();
    }

    @PatchMapping("/patient-drug/{patientDrugId}/notifi-taking")
    public ResponseEntity<?> patchNotifiTaking(
            @UserId UUID userId,
            @PathVariable("patientDrugId") UUID patientDrugId,
            @Valid @RequestBody NotifiTakingRequestDto requestDto) {
        return ResponseEntity.ok(
                notifiTakingService.patch(userId, patientDrugId, requestDto)
        );
    }

    @DeleteMapping("/notifi-taking/{notifiTakingId}")
    public ResponseEntity<?> deleteNotifiTaking(
           @UserId UUID userId,
           @PathVariable("notifiTakingId") UUID notifiTakingId) {
        notifiTakingService.delete(userId, notifiTakingId);
        return ResponseEntity.noContent().build();
    }
}
