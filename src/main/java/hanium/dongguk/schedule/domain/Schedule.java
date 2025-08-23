package hanium.dongguk.schedule.domain;

import hanium.dongguk.user.doctor.domain.UserDoctor;
import hanium.dongguk.user.patient.domain.UserPatient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "schedule_time")
    private LocalDateTime scheduleTime;

    @Column(name ="description", columnDefinition = "TEXT")
    private String warning;

    @Column(name = "symptom", columnDefinition = "TEXT")
    private String symptom;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "guidance", columnDefinition = "TEXT")
    private String guidance;

    @Column(name = "schedule_status")
    @Enumerated(EnumType.STRING)
    private EScheduleStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private UserPatient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private UserDoctor doctor;
}
