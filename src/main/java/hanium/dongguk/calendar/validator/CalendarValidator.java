package hanium.dongguk.calendar.validator;

import hanium.dongguk.calendar.exception.CalendarErrorCode;
import hanium.dongguk.global.exception.CommonException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CalendarValidator {

    public void validateFutureDate(LocalDate date) {
        if(date.isAfter(LocalDate.now())) {
            throw CommonException.type(CalendarErrorCode.FUTURE_DATE_NOT_ALLOWED);
        }
    }
}
