package hanium.dongguk.user.patient;

import hanium.dongguk.user.core.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("PATIENT")
public class UserPatient extends User {

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "due_date") // 출산 예정일
    private LocalDate dueDate;

    @Column(name = "pregnancy_weeks") //임신 주차
    private Integer pregnancyWeeks;

    @Column(name = "feeding") //수유 여부
    private boolean feeding;


}
