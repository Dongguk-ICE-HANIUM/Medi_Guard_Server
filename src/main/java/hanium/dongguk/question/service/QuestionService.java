package hanium.dongguk.question.service;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.calendar.exception.CalendarErrorCode;
import hanium.dongguk.calendar.service.CalendarRetriever;
import hanium.dongguk.calendar.service.CalendarSaver;
import hanium.dongguk.global.exception.CommonException;
import hanium.dongguk.question.domain.Question;
import hanium.dongguk.question.dto.request.SaveQuestionDto;
import hanium.dongguk.question.dto.request.SaveQuestionListRequestDto;
import hanium.dongguk.question.dto.request.UpdateQuestionDto;
import hanium.dongguk.question.dto.request.UpdateQuestionListRequestDto;
import hanium.dongguk.question.dto.response.QuestionResponseDto;
import hanium.dongguk.question.exception.QuestionErrorCode;
import hanium.dongguk.user.patient.domain.UserPatient;
import hanium.dongguk.user.patient.service.UserPatientRetriever;
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
    private final CalendarRetriever calendarRetriever;
    private final CalendarSaver calendarSaver;
    private final UserPatientRetriever userPatientRetriever;

    /**
     * 오늘의 질문 저장
     */
    @Transactional
    public void saveQuestions(UUID patientId, LocalDate date, SaveQuestionListRequestDto requestDto) {
        UserPatient userPatient = userPatientRetriever.getUserPatient(patientId);

        Calendar calendar = calendarRetriever.findByDateAndUserPatient(date, patientId)
                .orElseGet(() -> calendarSaver.save(Calendar.createForQuestion(userPatient, date)));



        List<SaveQuestionDto> questionList = requestDto.saveQuestionList();
        List<Question> questions = questionList.stream()
                .map(item -> Question.createQuestion(item.type(), item.answer(), calendar))
                .toList();

        questionSaver.saveAll(questions);

    }

    /**
     * 오늘의 질문 수정
     */
    @Transactional
    public void updateQuestions(UUID patientId, LocalDate date, UpdateQuestionListRequestDto requestDto) {
        Calendar calendar = calendarRetriever.findByDateAndUserPatient(date, patientId)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        List<Question> existingQuestions = questionRetriever.findByCalendarIdAndPatientId(calendar.getId(), patientId);

            if(!existingQuestions.isEmpty()) {
                throw CommonException.type(QuestionErrorCode.QUESTIONS_ALREADY_EXISTS);
            }

        List<UpdateQuestionDto> updateRequests = requestDto.updateQuestionList();


        updateRequests.forEach(updateRequest -> {
            Question question = existingQuestions.stream()
                    .filter(q -> q.getType() == updateRequest.type())
                    .findFirst()
                    .orElseThrow(() -> CommonException.type(QuestionErrorCode.QUESTION_NOT_FOUND_FOR_TYPE));

            question.updateAnswer(updateRequest.answer());
        });
    }

    /**
     * 특정 날짜 기준 질문 조회
     */
    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestionsByDate(UUID patientId, LocalDate date) {
        Calendar calendar = calendarRetriever.findByDateAndUserPatient(date, patientId)
                .orElseThrow(() -> CommonException.type(CalendarErrorCode.CALENDAR_NOT_FOUND));

        List<Question> questions = questionRetriever.findByCalendarIdAndPatientId(calendar.getId(), patientId);

        return QuestionResponseDto.of(questions);
    }
}