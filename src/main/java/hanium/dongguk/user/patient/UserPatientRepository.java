package hanium.dongguk.user.patient;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserPatientRepository extends JpaRepository<UserPatient, UUID> {
}