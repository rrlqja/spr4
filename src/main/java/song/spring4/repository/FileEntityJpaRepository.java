package song.spring4.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.spring4.entity.FileEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileEntityJpaRepository extends JpaRepository<FileEntity, Long> {

    @EntityGraph(attributePaths = {"board"})
    @Query("select f from FileEntity f where f.saveFileName = :saveFileName")
    Optional<FileEntity> findBySaveFileName(@Param(value = "saveFileName") String saveFileName);

    @EntityGraph(attributePaths = {"board"})
    @Query("select f from FileEntity f where f.board.id = :boardId")
    List<FileEntity> findByBoardId(@Param(value = "boardId") Long boardId);
}
