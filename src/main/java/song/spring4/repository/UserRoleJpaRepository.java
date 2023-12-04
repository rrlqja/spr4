package song.spring4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.spring4.domain.userrole.UserRole;
import song.spring4.entity.role.RoleName;

import java.util.Optional;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, Long> {

    @Query("select ur from UserRole ur where ur.user.id = :userId and ur.role.roleName = :roleName")
    Optional<UserRole> findByUserIdAndRoleName(@Param(value = "userId") Long userId,
                                               @Param(value = "roleName") RoleName roleName);

}
