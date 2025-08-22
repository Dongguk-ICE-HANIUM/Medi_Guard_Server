package hanium.dongguk.drug.patientdrug.util;

import hanium.dongguk.drug.patientdrug.domain.ETakingType;
import hanium.dongguk.drug.patientdrug.domain.TakingType;
import hanium.dongguk.drug.patientdrug.exception.TakingTypeErrorCode;
import hanium.dongguk.global.exception.CommonException;

public class TakingTypeFactory {
    public static TakingType classifyFromETakingType(
            ETakingType etakingType,
            Short takingInterval) {
        Short takingIntervalToUse;
        switch (etakingType) {
            case EVERY_DAY -> takingIntervalToUse = (short) 1;
            case PARTICULAR_INTERVAL,
                 PARTICULAR_DAY -> takingIntervalToUse = takingInterval;
            case SPECIFIC_DATE, NEED -> takingIntervalToUse = 0;
            default -> throw CommonException.type(TakingTypeErrorCode.INVALID_TAKING_TYPE);
        }
        return TakingType.create(etakingType, takingIntervalToUse);
    }
}
