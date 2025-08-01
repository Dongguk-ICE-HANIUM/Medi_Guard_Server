package hanium.dongguk.user.patient;

import hanium.dongguk.user.core.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("PATIENT")
@Table(name = "user_patient")
public class UserPatient extends User {

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "due_date", nullable = false) // 출산 예정일
    private LocalDate dueDate;

    @Column(name = "pregnancy_weeks", nullable = false) //임신 주차
    private Integer pregnancyWeeks;

    @Column(name = "feeding", nullable = false) //수유 여부
    private boolean feeding;


}
