package hanium.dongguk.auth.validator;

import hanium.dongguk.auth.exception.AuthErrorCode;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.core.domain.EStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AuthValidator {

    private static final int MAX_AGE = 100;
    private static final int MIN_AGE = 14;

    public void validateBirthday(LocalDate birthday){
        Period age = Period.between(birthday, LocalDate.now());

        if(age.getYears() > MAX_AGE || age.getYears() < MIN_AGE){
            throw CommonException.type(AuthErrorCode.INVALID_BIRTHDAY);
        }
    }

    public void validateDueDate(LocalDate dueDate){
        LocalDate now = LocalDate.now();

        if(dueDate.isBefore(now)){
            throw CommonException.type(AuthErrorCode.INVALID_DUE_DATE_PAST);
        }
    }


    public boolean isInactive(EStatus status){
        return status == EStatus.INACTIVE;
    }

    public void validatePending(EStatus status){
        if(status != EStatus.PENDING){
            throw CommonException.type(AuthErrorCode.ALREADY_COMPLETED_SIGNUP);
        }
    }

}
