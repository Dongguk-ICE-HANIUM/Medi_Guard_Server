package hanium.dongguk.question.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.calendar.exception.CalendarErrorCode;
import hanium.dongguk.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionValidator {

    public void validateQuestionBelongsToCalendar(Question question, Calendar calendar) {
        if (!question.getCalendar().equals(calendar)) {
            throw new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}