package hanium.dongguk.question.domain;

import hanium.dongguk.calendar.domain.Calendar;
import hanium.dongguk.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 프록시용 기본 생성자
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)

    @Column(name = "type", nullable = false)
    private EQuestionType type;   // 질문 타입 (Enum)

    @Column(name = "answer", columnDefinition = "TEXT", nullable = false)
    private String answer;  // 사용자가 작성한 답변

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    // 빌더
    @Builder
    private Question(final EQuestionType type, final String answer, final Calendar calendar) {
        this.type = type;
        this.answer = answer;
        this.calendar = calendar;
    }

    // 정적 팩토리 메서드
    public static Question createQuestion(final EQuestionType type, final String answer, final Calendar calendar) {
        return Question.builder()
                .type(type)
                .answer(answer)
                .calendar(calendar)
                .build();
    }

    // 도메인 로직 (예: 답변 수정)
    public void updateAnswer(final String newAnswer) {
        this.answer = newAnswer;
    }
}