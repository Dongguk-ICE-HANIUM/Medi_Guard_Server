package hanium.dongguk.user.core.validator;

import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.core.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final PasswordEncoder passwordEncoder;

    private static final int MAX_AGE = 100;
    private static final int MIN_AGE = 14;

    public void validateBirthday(LocalDate birthday){
        Period age = Period.between(birthday, LocalDate.now());

        if(age.getYears() > MAX_AGE || age.getYears() < MIN_AGE){
            throw CommonException.type(UserErrorCode.INVALID_BIRTHDAY);
        }
    }

    public void validateDueDate(LocalDate dueDate){
        LocalDate now = LocalDate.now();

        if(dueDate.isBefore(now)){
            throw CommonException.type(UserErrorCode.INVALID_DUE_DATE_PAST);
        }
    }
}
