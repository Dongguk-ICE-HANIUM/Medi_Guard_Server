package hanium.dongguk.question.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.user.core.exception.UserErrorCode;
import hanium.dongguk.question.domain.Question;

public class QuestionValidator {

    public static void validateQuestionBelongsToCalendar(Question question, Calendar calendar) {
        if (!question.getCalendar().equals(calendar)) {
            throw CommonException.type(UserErrorCode.NOT_FOUND_USER);
        }
    }
}