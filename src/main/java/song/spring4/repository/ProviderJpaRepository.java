package song.spring4.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.spring4.entity.oauth2.Provider;

import java.util.Optional;

@Repository
public interface ProviderJpaRepository extends JpaRepository<Provider, Long> {

    @EntityGraph(attributePaths = {"user"})
    Optional<Provider> findBySnsId(String snsId);

}
