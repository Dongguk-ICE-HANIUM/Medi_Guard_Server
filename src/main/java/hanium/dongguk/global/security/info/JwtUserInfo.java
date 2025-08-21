package hanium.dongguk.global.security.info;

import hanium.dongguk.user.core.domain.ERole;

import java.util.UUID;

public record JwtUserInfo(UUID userId, ERole role) {
}
