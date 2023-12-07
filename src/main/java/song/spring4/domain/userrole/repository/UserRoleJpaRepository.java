package song.spring4.domain.userrole.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.spring4.domain.userrole.entity.UserRole;
import song.spring4.domain.userrole.consts.Role;

import java.util.Optional;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long> {
    @EntityGraph(attributePaths = {"user"})
    @Query("select ur from UserRole ur where ur.user.id = :userId and ur.role = :role")
    Optional<UserRole> findByUserIdAndRole(@Param(value = "userId") Long userId,
                                           @Param(value = "role") Role role);

}
