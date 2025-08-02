package hanium.dongguk.calendar.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import hanium.dongguk.user.patient.UserPatient;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
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
    private LocalDateTime date;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "emotion", nullable = false)
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private UserPatient patient;

    // 빌더: 외부에서 받아야 하는 필드만
    @Builder
    private Calendar(final LocalDateTime date,
                     final String description,
                     final Emotion emotion,
                     final Question question,
                     final UserPatient patient) {
        this.date = date;
        this.description = description;
        this.emotion = emotion;
        this.question = question;
        this.patient = patient;
    }

    // 정적 팩토리 메서드
    public static Calendar create(final LocalDateTime date,
                                  final String description,
                                  final Emotion emotion,
                                  final Question question,
                                  final UserPatient patient) {
        return Calendar.builder()
                .date(date)
                .description(description)
                .emotion(emotion)
                .question(question)
                .patient(patient)
                .build();
    }

    // 도메인 메서드 (예: 감정/설명 업데이트)
    public void updateEmotion(final Emotion newEmotion, final String newDescription) {
        this.emotion = newEmotion;
        this.description = newDescription;
    }
}