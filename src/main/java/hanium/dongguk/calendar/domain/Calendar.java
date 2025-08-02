package hanium.dongguk.calendar.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import hanium.dongguk.question.domain.EQuestionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "calendar")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "emotion", nullable = false)
    @Enumerated(EnumType.STRING)
    private EEmotion emotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private EQuestionType questionType;

    // 빌더: 외부에서 받아야 하는 필드만
    @Builder
    private Calendar(final LocalDate date,
                     final String description,
                     final EEmotion emotion,
                     final EQuestionType question) {
        this.date = date;
        this.description = description;
        this.emotion = emotion;
        this.questionType = question;
    }

    // 정적 팩토리 메서드
    public static Calendar create(final LocalDate date,
                                  final String description,
                                  final EEmotion emotion,
                                  final EQuestionType question) {
        return Calendar.builder()
                .date(date)
                .description(description)
                .emotion(emotion)
                .question(question)
                .build();
    }

    // 도메인 메서드 (예: 감정/설명 업데이트)
    public void updateEmotion(final EEmotion newEmotion, final String newDescription) {
        this.emotion = newEmotion;
        this.description = newDescription;
    }
}