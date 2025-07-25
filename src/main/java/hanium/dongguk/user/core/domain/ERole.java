package hanium.dongguk.user.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERole {
    GUEST("GUEST", "ROLE_GUEST"),
    DOCTOR("DOCTOR", "ROLE_DOCTOR"),
    PATIENT("PATIENT", "ROLE_PATIENT"),
    ADMIN("ADMIN", "ROLE_ADMIN");

    private final String role;
    private final String securityRole;
}
