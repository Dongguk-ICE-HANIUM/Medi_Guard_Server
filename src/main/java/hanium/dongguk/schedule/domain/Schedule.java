package hanium.dongguk.schedule.domain;

import hanium.dongguk.user.doctor.domain.UserDoctor;
import hanium.dongguk.user.patient.domain.UserPatient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    private Schedule(LocalDateTime scheduleTime,
                    String warning,
                    String symptom,
                    String diagnosis,
                    String guidance,
                    EScheduleStatus status,
                    UserPatient patient,
                    UserDoctor doctor) {
        this.scheduleTime = scheduleTime;
        this.warning = warning;
        this.symptom = symptom;
        this.diagnosis = diagnosis;
        this.guidance = guidance;
        this.status = status;
        this.patient = patient;
        this.doctor = doctor;
    }


    public static Schedule create(LocalDateTime scheduleTime, UserPatient patient, UserDoctor doctor) {

        return Schedule.builder()
                .scheduleTime(scheduleTime)
                .patient(patient)
                .doctor(doctor)
                .status(EScheduleStatus.WAITING)
                .build();
    }
}
