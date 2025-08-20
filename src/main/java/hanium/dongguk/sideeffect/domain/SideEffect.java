package hanium.dongguk.sideeffect.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import hanium.dongguk.calendar.domain.CalendarDrug;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "side_effect")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SideEffect extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_drug_id", nullable = false)
    private CalendarDrug calendarDrug;

    @Builder
    private SideEffect(final String description, final CalendarDrug calendarDrug) {
        this.description = description;
        this.calendarDrug = calendarDrug;
    }

    public static SideEffect create(final String description, final CalendarDrug calendarDrug) {
        return SideEffect.builder()
                .description(description)
                .calendarDrug(calendarDrug)
                .build();
    }

    // 수정 API용 도메인 메서드
    public void updateDescription(final String newDescription) {
        this.description = newDescription;
    }
}