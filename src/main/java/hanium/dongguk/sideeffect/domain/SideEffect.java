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
@AllArgsConstructor
@Builder
public class SideEffect extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;   // 부작용 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_drug_id", nullable = false)
    private CalendarDrug calendarDrug;    // 어떤 복용 기록(CalendarDrug)에 속하는지
}