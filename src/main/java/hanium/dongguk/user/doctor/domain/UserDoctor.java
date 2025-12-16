package hanium.dongguk.user.doctor.domain;

import hanium.dongguk.user.core.domain.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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

    @Builder
    private UserDoctor(String serialId,
                       Email email,
                       String password,
                       String name,
                       ERole role,
                       EProvider provider,
                       EStatus status,
                       String hospitalName,
                       EDepartment department,
                       boolean isApproved) {
        super(serialId, email, password, name, role, provider, status);
        this.hospitalName = hospitalName;
        this.department = department;
        this.isApproved = isApproved;
    }

    public static UserDoctor create(final Email email,
                                    final String password,
                                    final String name,
                                    final String hospitalName,
                                    final EDepartment department){
        String serialId = UUID.randomUUID().toString();

        return UserDoctor.builder()
                .serialId(serialId)
                .email(email)
                .password(password)
                .name(name)
                .role(ERole.DOCTOR)
                .provider(EProvider.BASIC)
                .status(EStatus.ACTIVE)
                .hospitalName(hospitalName)
                .department(department)
                .isApproved(true)
                .build();
    }
}
