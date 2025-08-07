package hanium.dongguk.question.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.domain.CalendarRepository;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.global.exception.CalendarErrorCode;
import hanium.dongguk.global.exception.QuestionErrorCode;
import hanium.dongguk.question.domain.Question;
import hanium.dongguk.question.domain.QuestionRepository;
import hanium.dongguk.question.dto.request.QuestionSaveRequestDto;
import hanium.dongguk.question.dto.request.QuestionUpdateRequestDto;
import hanium.dongguk.question.dto.response.QuestionResponseDto;
import hanium.dongguk.user.patient.UserPatient;
import hanium.dongguk.user.patient.UserPatientRepository;
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

    private final QuestionRepository questionRepository;
    private final CalendarRepository calendarRepository;
    private final UserPatientRepository userPatientRepository;

    /**
     * 오늘의 질문 저장
     */
    @Transactional
    public QuestionResponseDto saveQuestions(UUID patientId, LocalDate date, QuestionSaveRequestDto requestDto) {
        UserPatient userPatient = userPatientRepository.findById(patientId)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS));

        Calendar calendar = calendarRepository.findByDateAndUserPatient(date, userPatient)
                .orElseGet(() -> calendarRepository.save(
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

        questionRepository.saveAll(questions);

        return QuestionResponseDto.of(questions);
    }

    /**
     * 오늘의 질문 수정
     */
    @Transactional
    public QuestionResponseDto updateQuestions(UUID patientId, LocalDate date, QuestionUpdateRequestDto requestDto) {
        UserPatient userPatient = userPatientRepository.findById(patientId)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS));

        Calendar calendar = calendarRepository.findByDateAndUserPatient(date, userPatient)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.CALENDAR_NOT_FOUND));

        List<Question> updatedQuestions = requestDto.questionList().stream()
                .map(item -> {
                    Question question = questionRepository.findById(item.id())
                            .orElseThrow(() -> new CommonException(QuestionErrorCode.QUESTION_NOT_FOUND));

                    if (!question.getCalendar().equals(calendar)) {
                        throw new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS);
                    }

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
        UserPatient userPatient = userPatientRepository.findById(patientId)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.UNAUTHORIZED_ACCESS));

        Calendar calendar = calendarRepository.findByDateAndUserPatient(date, userPatient)
                .orElseThrow(() -> new CommonException(CalendarErrorCode.CALENDAR_NOT_FOUND));

        List<Question> questions = questionRepository.findByCalendarId(calendar.getId());

        return QuestionResponseDto.of(questions);
    }
}