package hanium.dongguk.drug.patientdrug.domain;

import hanium.dongguk.drug.drug.domain.Drug;
import hanium.dongguk.global.base.BaseTimeEntity;
import hanium.dongguk.user.patient.domain.UserPatient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Table(name = "patient_drug")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatientDrug extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_At", nullable = false)
    private LocalDate startAt;

    @Column(name = "end_At", nullable = false)
    private LocalDate endAt;

    @Column(name = "per_day", nullable = false)
    private Short perDay;

    @Column(name = "amount", precision = 10, scale = 3, nullable = false)
    private BigDecimal amount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_essential", nullable = false)
    private Boolean isEssential;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_patient_id", nullable = false)
    private UserPatient userPatient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taking_type_id", nullable = false)
    private TakingType takingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_group_id")
    private DrugGroup drugGroup;

    @Builder
    private PatientDrug(
            final String name,
            final LocalDate startAt,
            final LocalDate endAt,
            final Short perDay,
            final BigDecimal amount,
            final String description,
            final Boolean isEssential,
            final Boolean isActive,
            final UserPatient userPatient,
            final Drug drug,
            final TakingType takingType,
            final DrugGroup drugGroup
    ) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.perDay = perDay;
        this.amount = amount;
        this.description = description;
        this.isEssential = isEssential;
        this.isActive = isActive;
        this.userPatient = userPatient;
        this.drug = drug;
        this.takingType = takingType;
        this.drugGroup = drugGroup;
    }

    public static PatientDrug create(
            final String name,
            final LocalDate startAt,
            final LocalDate endAt,
            final Short perDay,
            final BigDecimal amount,
            final String description,
            final UserPatient userPatient,
            final Drug drug,
            final TakingType takingType,
            final DrugGroup drugGroup
    ) {
        return PatientDrug.builder()
                .name(name)
                .startAt(startAt)
                .endAt(endAt)
                .perDay(perDay)
                .amount(amount)
                .description(description)
                .isEssential(true)
                .isActive(true)
                .userPatient(userPatient)
                .drug(drug)
                .takingType(takingType)
                .drugGroup(drugGroup)
                .build();
    }

    public void update(final Boolean isEssential) {
        this.isEssential = isEssential;
    }
}
