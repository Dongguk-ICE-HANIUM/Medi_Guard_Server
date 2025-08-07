package hanium.dongguk.calendar.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
//import hanium.dongguk.user.patient.PatientDrug; 패키지 경로는 실제 위치에 맞게 수정 예정
import jakarta.persistence.*;
import lombok.*;
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

    /**
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_drug_id", nullable = false)
    private PatientDrug patientDrug; // 어떤 환자의 약물인지
     */

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

    //patient drug 연동 후 밑에 코드 사용 예정
    /**
    @Builder
    private CalendarDrug(final Calendar calendar,
                         final PatientDrug patientDrug,
                         final Integer timeSlot) {
        this.calendar = calendar;
        this.patientDrug = patientDrug;
        this.timeSlot = timeSlot;
    }

    // 정적 팩토리 메서드
    public static CalendarDrug create(final Calendar calendar,
                                      final PatientDrug patientDrug,
                                      final Integer timeSlot) {
        return CalendarDrug.builder()
                .calendar(calendar)
                .patientDrug(patientDrug)
                .timeSlot(timeSlot)
                .build();
    }
    */
}