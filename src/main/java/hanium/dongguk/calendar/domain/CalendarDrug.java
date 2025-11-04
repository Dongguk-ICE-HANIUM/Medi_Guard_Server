package hanium.dongguk.calendar.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
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
    @JoinColumn(name = "patient_drug_id", nullable = false)
    private PatientDrug patientDrug; // 어떤 환자의 약물인지

    @Column(name = "time_slot", nullable = false)
    private Integer timeSlot;  // 예: 아침=1, 점심=2, 저녁=3 같은 slot 번호

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Builder
    private CalendarDrug(final PatientDrug patientDrug,
                         final Integer timeSlot,
                         final LocalDate recordDate) {
        this.patientDrug = patientDrug;
        this.timeSlot = timeSlot;
        this.recordDate = recordDate;
    }

    // 정적 팩토리 메서드
    public static CalendarDrug create(final PatientDrug patientDrug,
                                      final Integer timeSlot) {
        return CalendarDrug.builder()
                .patientDrug(patientDrug)
                .timeSlot(timeSlot)
                .recordDate(LocalDate.now())
                .build();
    }
}