package hanium.dongguk.schedule.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EScheduleStatus {
    WAITING("진료예정"),
    STARTED("진료시작"),
    IN_PROGRESS("진료중"),
    COMPLETED("진료완료")
    ;

    private final String displayName;
}
