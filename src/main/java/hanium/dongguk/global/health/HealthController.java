package hanium.dongguk.global.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Health", description = "Health Check API")
public class HealthController {

    @Operation(summary = "Health Check API")
    @GetMapping("/healthz")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("ok");
    }
}
