package hanium.dongguk.question.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.service.CalendarRetriever;
import hanium.dongguk.calendar.service.CalendarSaver;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.calendar.exception.CalendarErrorCode;
import hanium.dongguk.question.exception.QuestionErrorCode;
import hanium.dongguk.user.core.exception.UserErrorCode;
import hanium.dongguk.question.domain.Question;
import hanium.dongguk.question.dto.request.QuestionSaveRequestDto;
import hanium.dongguk.question.dto.request.QuestionUpdateRequestDto;
import hanium.dongguk.question.dto.response.QuestionResponseDto;
import hanium.dongguk.user.patient.UserPatient;
import hanium.dongguk.user.core.domain.User;
import hanium.dongguk.user.core.domain.UserRepository;
import hanium.dongguk.calendar.domain.EEmotion;
import hanium.dongguk.question.domain.EQuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRetriever questionRetriever;
    private final QuestionSaver questionSaver;
    private final QuestionValidator questionValidator;
    private final CalendarRetriever calendarRetriever;
    private final CalendarSaver calendarSaver;
    private final UserRepository userRepository;

    /**
     * 오늘의 질문 저장
     */
    @Transactional
    public QuestionResponseDto saveQuestions(UUID patientId, LocalDate date, QuestionSaveRequestDto requestDto) {
        User user = userRepository.findById(patientId)
                .orElseThrow(() -> CommonException.type(UserErrorCode.NOT_FOUND_USER));
        
        if (!(user instanceof UserPatient)) {
            throw CommonException.type(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }
        
        UserPatient userPatient = (UserPatient) user;

        Calendar calendar = calendarRetriever.findByDateAndUserPatient(date, userPatient)
                .orElseGet(() -> calendarSaver.save(
                        Calendar.create(
                                date,
                                "자동 생성된 캘린더입니다.",         // description
                                EEmotion.NEUTRAL,                        // emotion: enum 기본값
                                EQuestionType.PHYSICAL_SYMPTOMS,         // questionType: enum 기본값
                                userPatient
                        )
                ));

        List<Question> questions = requestDto.questionList().stream()
                .map(item -> Question.createQuestion(item.type(), item.answer(), calendar))
                .toList();

        questionSaver.saveAll(questions);

        return QuestionResponseDto.of(questions);
    }

    /**
     * 오늘의 질문 수정
     */
    @Transactional
    public QuestionResponseDto updateQuestions(UUID patientId, LocalDate date, QuestionUpdateRequestDto requestDto) {
        User user = userRepository.findById(patientId)
                .orElseThrow(() -> CommonException.type(UserErrorCode.NOT_FOUND_USER));
        
        if (!(user instanceof UserPatient)) {
            throw CommonException.type(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }
        
        UserPatient userPatient = (UserPatient) user;

        Calendar calendar = calendarRetriever.findByDateAndUserPatient(date, userPatient)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        List<Question> updatedQuestions = requestDto.questionList().stream()
                .map(item -> {
                    Question question = questionRetriever.findById(item.id())
                            .orElseThrow(() -> CommonException.type(QuestionErrorCode.QUESTION_NOT_FOUND));

                    questionValidator.validateQuestionBelongsToCalendar(question, calendar);

                    question.updateAnswer(item.answer());
                    return question;
                })
                .toList();

        return QuestionResponseDto.of(updatedQuestions);
    }

    /**
     * 특정 날짜 기준 질문 조회
     */
    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestionsByDate(UUID patientId, LocalDate date) {
        User user = userRepository.findById(patientId)
                .orElseThrow(() -> CommonException.type(UserErrorCode.NOT_FOUND_USER));
        
        if (!(user instanceof UserPatient)) {
            throw CommonException.type(CalendarErrorCode.UNAUTHORIZED_ACCESS);
        }
        
        UserPatient userPatient = (UserPatient) user;

        Calendar calendar = calendarRetriever.findByDateAndUserPatient(date, userPatient)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        List<Question> questions = questionRetriever.findByCalendarId(calendar.getId());

        return QuestionResponseDto.of(questions);
    }
}