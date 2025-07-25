package hanium.dongguk.user.core.domain;

import hanium.dongguk.user.core.dto.UserSecurityForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u.id as id, u.role as role, u.password as password from User u where u.email = :email")
    Optional<UserSecurityForm> findUserSecurityFromByEmail(Email email);

    @Query("select u.id as id, u.role as role from User u where u.id = :id")
    Optional<UserSecurityForm> findUserSecurityFromById(@Param("id") UUID id);

    Optional<User> findById(UUID id);
}