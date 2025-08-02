package hanium.dongguk.question.domain;

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
    private QuestionType type;   // 질문 타입 (Enum)

    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;  // 사용자가 작성한 답변

    // 빌더는 필요한 필드만 열어줌 (id는 제외)
    @Builder
    private Question(final QuestionType type, final String answer) {
        this.type = type;
        this.answer = answer;
    }

    // 정적 팩토리 메서드
    public static Question createQuestion(final QuestionType type, final String answer) {
        return Question.builder()
                .type(type)
                .answer(answer)
                .build();
    }

    // 도메인 로직 (예: 답변 수정)
    public void updateAnswer(final String newAnswer) {
        this.answer = newAnswer;
    }
}