package hanium.dongguk.user.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EProvider {
    BASIC("BASIC"),
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    APPLE("APPLE");

    private final String name;
}

