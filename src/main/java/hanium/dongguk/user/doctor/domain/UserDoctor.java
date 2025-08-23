package hanium.dongguk.user.doctor.domain;

import hanium.dongguk.user.core.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DiscriminatorValue("DOCTOR")
@Table(name = "user_doctor")
public class UserDoctor extends User {

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "department")
    @Enumerated(EnumType.STRING)
    private EDepartment department;

    @Column(name = "is_approve")
    private boolean isApproved;
}
