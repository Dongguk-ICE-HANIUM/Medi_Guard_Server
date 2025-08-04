package hanium.dongguk.calendar.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "calendar_drug")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarDrug extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;  // 어떤 캘린더(날짜)에 속하는 기록인지

    @Column(name = "time_slot", nullable = false)
    private Integer timeSlot;  // 예: 아침=1, 점심=2, 저녁=3 같은 slot 번호

    // 빌더: id는 제외
    @Builder
    private CalendarDrug(final Calendar calendar,
                         final Integer timeSlot) {
        this.calendar = calendar;
        this.timeSlot = timeSlot;
    }

    // 정적 팩토리 메서드
    public static CalendarDrug create(final Calendar calendar,
                                      final Integer timeSlot) {
        return CalendarDrug.builder()
                .calendar(calendar)
                .timeSlot(timeSlot)
                .build();
    }
}