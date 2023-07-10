package song.spring4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.spring4.entity.Board;

@Repository
public interface BoardJpaRepository extends JpaRepository<Board, Long> {

}
