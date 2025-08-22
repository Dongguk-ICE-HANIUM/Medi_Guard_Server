package hanium.dongguk.drug.patientdrug.domain;

import hanium.dongguk.drug.patientdrug.exception.TakingTypeErrorCode;
import hanium.dongguk.global.exception.CommonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ETakingType {
    EVERY_DAY("매일"),
    PARTICULAR_INTERVAL("특정일 간격"),
    PARTICULAR_DAY("특정 요일"),
    SPECIFIC_DATE("특정 날짜"),
    NEED("필요 시 복용")
    ;

    private final String name;

    public static ETakingType findByName(String name) {
        for (ETakingType type : ETakingType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw CommonException.type(TakingTypeErrorCode.NOT_FOUND_TAKING_TYPE);
    }
}
