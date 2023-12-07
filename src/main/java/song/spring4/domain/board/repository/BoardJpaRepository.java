package song.spring4.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.spring4.domain.board.entity.Board;

import java.util.Optional;

@Repository
public interface BoardJpaRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"user"})
    Optional<Board> findEntityGraphById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Page<Board> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("select b from Board b where b.user.username like %:username%")
    Page<Board> findAllByUsernameLike(@Param(value = "username") String username, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("select b from Board b where b.title like %:title%")
    Page<Board> findAllByTitleLike(@Param(value = "title") String title, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("select b from Board b where b.content like %:content%")
    Page<Board> findAllByContentLike(@Param(value = "content") String content, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Board b set b.title = concat(b.title, :updateTitle) where b.title like %:title%")
    Integer updateBoardTitle(@Param(value = "title") String title, @Param(value = "updateTitle") String updateTitle);

}
