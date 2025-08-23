package hanium.dongguk.notification.taking.domain;

import hanium.dongguk.drug.patientdrug.domain.PatientDrug;
import hanium.dongguk.notification.core.domain.Notification;
import hanium.dongguk.user.patient.domain.UserPatient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("NOTIFI_TAKING")
@Table(name = "notifi_taking")
public class NotifiTaking extends Notification {
    @Column(name = "taking_time")
    private LocalTime takingTime;

    @Column(name = "is_active")
    boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_drug_id")
    PatientDrug patientDrug;

    @Builder
    private NotifiTaking(
            final String description,
            final UserPatient patientUser,
            final LocalTime takingTime,
            final boolean isActive,
            final PatientDrug patientDrug) {
        super(description, patientUser);
        this.takingTime = takingTime;
        this.isActive = isActive;
        this.patientDrug = patientDrug;
    }

    public static NotifiTaking create(
            final UserPatient patientUser,
            final LocalTime takingTime,
            final boolean isActive,
            final PatientDrug patientDrug) {
        return NotifiTaking.builder()
                .description("")
                .patientUser(patientUser)
                .takingTime(takingTime)
                .isActive(isActive)
                .patientDrug(patientDrug)
                .build();
    }

    public void update(
            final LocalTime takingTime,
            boolean isActive) {
        this.takingTime = takingTime;
        this.isActive = isActive;
    }
}
