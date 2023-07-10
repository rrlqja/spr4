package song.spring4.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.spring4.entity.Board;

import java.util.Optional;

@Repository
public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"writer"})
    Optional<Board> findById(Long id);

    @EntityGraph(attributePaths = {"writer"})
    Page<Board> findAll(Pageable pageable);
}
