package hanium.dongguk.user.doctor.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDoctorRepository extends JpaRepository<UserDoctor, UUID> {
}
