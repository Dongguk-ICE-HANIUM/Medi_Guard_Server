package hanium.dongguk.user.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EStatus {
    INACTIVE("INACTIVE"),
    ACTIVE("ACTIVE"),
    PENDING("PENDING");

    private final String name;
}
