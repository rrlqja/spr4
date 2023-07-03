package song.spring4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.spring4.entity.FindPasswordToken;

@Repository
public interface FindPasswordTokenJpaRepository extends JpaRepository<FindPasswordToken, Long> {

}
