package song.spring4.domain.oauth2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.spring4.domain.oauth2.entity.Sns;

import java.util.Optional;

@Repository
public interface SnsJpaRepository extends JpaRepository<Sns, String> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Sns> findBySnsEmail(String snsEmail);

}
