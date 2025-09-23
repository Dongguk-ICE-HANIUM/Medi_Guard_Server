package hanium.dongguk.user.doctor.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserDoctorRepository extends JpaRepository<UserDoctor, UUID> {
    @Query("SELECT d FROM UserDoctor d WHERE d.department = :department " +
            "AND (:nameKeyword IS NULL OR d.name LIKE %:nameKeyword%)")
    List<UserDoctor> searchUserDoctorList(@Param("department") EDepartment department,
                                          @Param("nameKeyword") String nameKeyword);
}
