package song.spring4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.spring4.entity.EmailVerificationToken;

import java.util.Optional;

@Repository
public interface EmailVerificationJpaRepository extends JpaRepository<EmailVerificationToken, Long> {

    @Query("select t from EmailVerificationToken t where t.email = :email")
    Optional<EmailVerificationToken> findByEmail(@Param(value = "email") String email);

    @Query("select t from EmailVerificationToken t where t.token = :token")
    Optional<EmailVerificationToken> findByToken(@Param(value = "token") String token);

}
