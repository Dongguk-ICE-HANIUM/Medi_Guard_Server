package hanium.dongguk.drug.patientdrug.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import hanium.dongguk.user.patient.domain.UserPatient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Table(name = "drug_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrugGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_patient_id", nullable = false)
    private UserPatient userPatient;

    @Builder
    private DrugGroup(
            final String name,
            final Boolean isActive,
            final UserPatient userPatient) {
        this.name = name;
        this.isActive = isActive;
        this.userPatient = userPatient;
    }

    public static DrugGroup create(
            final String name,
            final UserPatient userPatient) {
        return DrugGroup.builder()
                .name(name)
                .isActive(true)
                .userPatient(userPatient)
                .build();
    }
}
