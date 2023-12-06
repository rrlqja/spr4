package song.spring4.domain.file.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.spring4.domain.file.FileEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileEntityJpaRepository extends JpaRepository<FileEntity, Long> {
    @EntityGraph(attributePaths = {"board"})
    @Query("select f from FileEntity f where f.savedFileName = :savedFileName")
    Optional<FileEntity> findBySavedFileName(@Param(value = "savedFileName") String savedFileName);

    @Query("select f from FileEntity f where f.savedFileName in :savedFileNameList")
    List<FileEntity> findAllBySavedFileNameList(@Param("savedFileNameList") List<String> savedFileNameList);
}
