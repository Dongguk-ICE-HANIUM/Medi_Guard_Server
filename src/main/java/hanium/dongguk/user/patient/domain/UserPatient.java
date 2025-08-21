package hanium.dongguk.user.patient.domain;

import hanium.dongguk.user.core.domain.*;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

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

    @Builder
    private UserPatient(String serialId,
                        Email email,
                        String password,
                        String name,
                        ERole role,
                        EProvider provider,
                        EStatus status,
                        LocalDate birth,
                        Integer height,
                        Integer weight,
                        LocalDate dueDate,
                        Integer pregnancyWeeks,
                        boolean feeding) {
        super(serialId, email, password, name, role, provider, status);
        this.birth = birth;
        this.height = height;
        this.weight = weight;
        this.dueDate = dueDate;
        this.pregnancyWeeks = pregnancyWeeks;
        this.feeding = feeding;
    }

    public static UserPatient normalCreate(final Email email,
                                           final String password,
                                           final String name,
                                           final LocalDate birth,
                                           final Integer height,
                                           final Integer weight,
                                           final LocalDate dueDate,
                                           final Integer pregnancyWeeks,
                                           final boolean feeding){
        String serialId = UUID.randomUUID().toString();

        return UserPatient.builder()
                .serialId(serialId)
                .email(email)
                .password(password)
                .name(name)
                .role(ERole.PATIENT)
                .provider(EProvider.BASIC)
                .status(EStatus.ACTIVE)
                .birth(birth)
                .height(height)
                .weight(weight)
                .dueDate(dueDate)
                .pregnancyWeeks(pregnancyWeeks)
                .feeding(feeding)
                .build();
    }

    public static UserPatient googleCreate(final String providerId, final String name){

        return UserPatient.builder()
                .serialId(providerId)
                .password(UUID.randomUUID().toString())
                .name(name)
                .role(ERole.PATIENT)
                .provider(EProvider.GOOGLE)
                .status(EStatus.PENDING)
                .build();
    }
}
