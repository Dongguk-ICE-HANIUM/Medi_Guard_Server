package hanium.dongguk.patientdrug.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import hanium.dongguk.user.patient.UserPatient;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "patient_drug")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientDrug extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private UserPatient userPatient;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    // 향후 연동 예정: 복용 방식, 질병, 약물 그룹 등
    // @ManyToOne
    // private DrugGroup drugGroup;

    @Builder
    private PatientDrug(UserPatient userPatient,
                        LocalDateTime startAt,
                        LocalDateTime endAt) {
        this.userPatient = userPatient;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static PatientDrug create(UserPatient userPatient,
                                     LocalDateTime startAt,
                                     LocalDateTime endAt) {
        return PatientDrug.builder()
                .userPatient(userPatient)
                .startAt(startAt)
                .endAt(endAt)
                .build();
    }
}