package hanium.dongguk.sideeffect.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.sideeffect.dto.request.SaveSideEffectRequestDto;
import hanium.dongguk.sideeffect.dto.request.UpdateSideEffectRequestDto;
import hanium.dongguk.sideeffect.dto.response.SideEffectListResponseDto;
import hanium.dongguk.sideeffect.service.SideEffectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/side-effect")
@RequiredArgsConstructor
public class SideEffectController implements SideEffectApiSwagger {

    private final SideEffectService sideEffectService;

    @Override
    @GetMapping
    public ResponseEntity<SideEffectListResponseDto> getSideEffect(
            @UserId UUID userId) {
        return ResponseEntity.ok(sideEffectService.getSideEffect(userId));
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> saveSideEffect(
            @Valid @RequestBody SaveSideEffectRequestDto requestDto,
            @UserId UUID userId) {
        sideEffectService.saveSideEffect(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @PatchMapping("/{sideEffectId}")
    public ResponseEntity<Void> updateSideEffect(
            @PathVariable UUID sideEffectId,
            @Valid @RequestBody UpdateSideEffectRequestDto requestDto,
            @UserId UUID userId) {
        sideEffectService.updateSideEffect(sideEffectId, userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{sideEffectId}")
    public ResponseEntity<Void> deleteSideEffect(
            @PathVariable UUID sideEffectId,
            @UserId UUID userId) {
        sideEffectService.deleteSideEffect(sideEffectId, userId);
        return ResponseEntity.ok().build();
    }
}
